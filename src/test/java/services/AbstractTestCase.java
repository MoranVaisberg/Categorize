package services;

import json.JSONConvertor;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class responsible to load test cases
 */
public class AbstractTestCase {

    private static final String PHRASE_KEY = "phrase";
    private static final String EXPECTED_KEY = "expected";

    protected String phrase;
    protected Set<String> dictionary;
    protected Map<String, Integer> expectedCategories;

    protected void loadTestData (String fileName) throws IOException, ParseException {
        LoadTest loadTest = new LoadTest().invoke(fileName);
        phrase = loadTest.getPhrase();
        dictionary = loadTest.getDictionary();
        expectedCategories = loadTest.getExpectedcategories();
    }

    protected String getTestFileLocation(Class clazz, String fileName){
        return clazz.getResource( fileName).getPath();
    }

    private class LoadTest {
        private String phrase;
        private Set<String> dictionary;
        private Map<String, Integer> expectedCategories;

        public String getPhrase() {
            return phrase;
        }

        public Set<String> getDictionary() {
            return dictionary;
        }

        public Map<String, Integer> getExpectedcategories() {
            return expectedCategories;
        }

        public LoadTest invoke(String fileName) throws IOException, ParseException {
            JSONObject testObj = JSONConvertor.loadFileToJson(fileName);
            phrase = (String) JSONConvertor.getObjectByKey(testObj, PHRASE_KEY);
            dictionary = JSONConvertor.getCategories(testObj);
            expectedCategories = getExpected(testObj);
            return this;
        }

        /**
         * Get expected JSONObject and transforms it to MAP
         */
        private Map<String, Integer> getExpected (JSONObject jsonObject) {
            Map<String, Integer> expectedCategories = new TreeMap<String, Integer>();
            if (jsonObject != null) {
                JSONObject expected = (JSONObject)jsonObject.get(EXPECTED_KEY);
                if (expected != null) {
                    // Transforms JSONObject to MAP
                    for (Object obj : expected.entrySet()) {
                        Map.Entry entry = (Map.Entry) obj;
                        String key = (String)entry.getKey();
                        Integer value = entry.getValue() != null ? ((Long)entry.getValue()).intValue() : null;
                        expectedCategories.put(key,value);
                    }
                }
            }
            return expectedCategories;
        }
    }
}
