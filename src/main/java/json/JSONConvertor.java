package json;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A Utility which helps :
 *  - to load JSON file into JSON Object
 *  - to convert JSON object to a metadata object
 */
public class JSONConvertor {

    private static final String INPUT_KEY = "input";
    private static final String CATEGORIES_KEY = "categories";


    /**
     * Loads json file into JSON Object
     */
    public static JSONObject loadFileToJson (String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(fileName);
        JSONObject jsonObject = null;
        if (fileReader != null) {
            Object obj = parser.parse(new FileReader(fileName));
            jsonObject = (JSONObject) obj;
        }
        return jsonObject;
    }

    /**
     * Generates Collection of categories from a JSONObject
     */
    public static Set<String> getCategories(JSONObject categoriesJson){

        Set<String> categories = null;
        if (categoriesJson != null) {
            JSONObject inputKey = (JSONObject)categoriesJson.get(INPUT_KEY);
            if (inputKey != null) {
                JSONArray categoriesArray = (JSONArray) inputKey.get(CATEGORIES_KEY);
                if (categoriesArray != null) {
                    Iterator iter = categoriesArray.iterator();
                    if (iter != null) {
                        categories = new HashSet<String>();
                        while (iter.hasNext()) {
                            categories.add((String) iter.next());
                        }
                    }
                }
            }
        }
        return categories;

    }

    /**
     * Creates JSONObject from Categories Map
     */
    public static JSONObject getCategoriesPosition(Map<String, Integer> categoriesLocation) {
        JSONObject categoriesObject = null;
        if (categoriesLocation.size() != 0) {
            categoriesObject = new JSONObject();
            categoriesObject.putAll(categoriesLocation);
        }
        return categoriesObject;
    }

    /**
     * Provides Value Object by given key from JSON Object
     */
    public static Object getObjectByKey (JSONObject jsonObject, String key) {
        Object value = null;
        if (jsonObject != null) {
            JSONObject inputKey = (JSONObject)jsonObject.get(INPUT_KEY);
            if (inputKey != null) {
                value = inputKey.get(key);
            }
        }
        return value;
    }


}
