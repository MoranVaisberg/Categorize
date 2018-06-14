package services;

import org.junit.Test;

import static org.junit.Assert.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

/**
 * This class responsible to test algorithm in CategoriesProcessor,
 * which finds matches between categories in query param
 * and the dictionary and their positions
 */

public class CategoriesEngineTest extends AbstractTestCase {

    @Test
    public void getCategoriesAndLocations() throws IOException, ParseException {

        runTest(getTestFileLocation( CategoriesEngineTest.class,"../data/CategoriesTest_1.json"));
        runTest(getTestFileLocation( CategoriesEngineTest.class,"../data/CategoriesTest_2.json"));
        runTest(getTestFileLocation( CategoriesEngineTest.class,"../data/CategoriesTest_3.json"));
        runTest(getTestFileLocation( CategoriesEngineTest.class,"../data/CategoriesTest_4.json"));

    }

    private void runTest(String fileName) throws IOException, ParseException {
        loadTestData(fileName);

        CategoriesEngine engine = new CategoriesEngine();
        Map<String, Integer> actualCategories = engine.getCategoriesAndPositions(dictionary, phrase);
        assertTrue(expectedCategories.size() == actualCategories.size());
        assertTrue(expectedCategories.equals(actualCategories));
    }


}