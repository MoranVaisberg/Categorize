package services;

import exception.CategorizationException;
import json.JSONConvertor;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * This class tests that generated JSON contains all categories and their locations
 * from the given phrase in query param
 */
public class CategorizationServiceTest extends AbstractTestCase {

    @Test
    public void getResponse() throws CategorizationException {
        runTest(getTestFileLocation( CategorizationServiceTest.class, "../data/CategoriesTest_1.json"));
    }
    private void runTest(String fileName) throws CategorizationException {

        try {
            loadTestData(fileName);
            CategorizationService service = new CategorizationService();
            Response obj = service.getResponse(phrase);
            String actualResponse = obj.getEntity().toString();
            String expectedResponse = JSONConvertor.getCategoriesPosition(expectedCategories).toJSONString();
            assertTrue(expectedResponse.equals(actualResponse));
        } catch (IOException e) {
            throw new CategorizationException(e);
        } catch (ParseException e) {
            throw new CategorizationException(e);
        }

    }
}