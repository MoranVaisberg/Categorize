package services;

import exception.CategorizationException;
import json.JSONConvertor;
import log.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * This class provides service for categorization
 * Look for categories in the dictionary and their position in the phrase
 * e.g. Vice President; Sales; Marketing
 */
public class CategorizationService {

    private static final String PROPERTY_FILE =  "C:/leadspace/property.json";
    private static final String DICTIONARY_FILE = "dictionaryFile";
    private static final String LOG_FILE = "logFile";
    private static final String EMPTY = "";
    private static final int SUCCESS = 200;
    private static final int ERROR = 500;

    public CategorizationService () {
    }

    /**
     * Generates a JSON String, which contains category and category position in the phrase
     * e.g.
     * {
     *    "Vice President" : 0,
     *    "Sales" : 18,
     *    "Marketing" : 28
     *  }
     *  phrase = "Vice President of Sales and Marketing"
     * @param phrase - QueryParam from GET method url
     * @return Response
     */
    public Response getResponse(String phrase) throws CategorizationException {

        JSONObject responseJson= null;
        String response = null;
        int status = SUCCESS;
        CategoriesEngine engine = new CategoriesEngine();

        try {
            Set<String> dictionary = getDictionary();

            Map<String, Integer> categoryPosition = engine.getCategoriesAndPositions(dictionary, phrase);

            responseJson = JSONConvertor.getCategoriesPosition(categoryPosition);
            response = (responseJson!=null)?responseJson.toJSONString():EMPTY;

        } catch (CategorizationException e) {
            status = ERROR;
            response = e.getShortMessage();
            try {
                writeLogMessage(LOG_FILE, e.getMessage());
            } catch (CategorizationException e1) {
            }
            throw e;
        }

        return Response.status(status)
                .entity(response)
                .build();
    }

    /**
     * Loads the dictionary
     */
    private Set<String> getDictionary() throws CategorizationException {
        Set<String> dictionary = null;
        try {

            JSONObject configurationFile = JSONConvertor.loadFileToJson(PROPERTY_FILE);
            String dictionaryFileName = getFileName(configurationFile, DICTIONARY_FILE);
            JSONObject dictionaryCategories = JSONConvertor.loadFileToJson(dictionaryFileName);
            dictionary = JSONConvertor.getCategories(dictionaryCategories);

            if (dictionary == null || dictionary.size() ==0){
                throw new CategorizationException("No Categories in Dictionary");
            }

        } catch (IOException e) {
            throw new CategorizationException(e);
        } catch (ParseException e) {
            throw new CategorizationException(e);
        }

        return dictionary;
    }

    private String getFileName(JSONObject configurationFile, String fileCategory) {
        return (String)JSONConvertor.getObjectByKey(configurationFile, fileCategory);
    }

    private void writeLogMessage (String fileCategory, String errorMessage) throws CategorizationException {
        try {

            JSONObject configurationFile = JSONConvertor.loadFileToJson(PROPERTY_FILE);
            String logFileName = getFileName(configurationFile, fileCategory);
            Logger.writeLogMessage(logFileName, errorMessage);

        } catch (IOException e) {
            throw new CategorizationException(e);
        } catch (ParseException e) {
            throw new CategorizationException(e);
        }
    }


}
