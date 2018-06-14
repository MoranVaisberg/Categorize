package services;

import exception.CategorizationException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * This class provides Rest Service
 */
@Path("/")
public class RestService {

    /**
     * GET method for Categorization
     * url example: http://localhost:8080/categorize?phrase=Vice+President+of+Sales+and+Marketing
     * @param phrase is a list of categories, which should be found in the dictionary
     */
    @GET
    @Path("categorize")
    public Response categorize(@QueryParam("phrase") String phrase ) throws CategorizationException {
        CategorizationService service = new CategorizationService();
        return service.getResponse(phrase);
    }
}
