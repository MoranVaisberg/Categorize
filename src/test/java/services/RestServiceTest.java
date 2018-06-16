package services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;


public class RestServiceTest {

    private static final String URI = "http://localhost:8080/categorize?phrase=Vice+President+of+Sales+and+Marketing";
    private static final int SUCCESS = 200;
    private static final String MEDIA_TYPE = "application/json";

    @Test
    public void categorize() {
        try {
            Client client = Client.create();

            WebResource webResource = client.resource(getBaseURI());

            ClientResponse response = webResource.accept(MEDIA_TYPE).get(ClientResponse.class);

            if (response.getStatus() != SUCCESS) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            String output = response.getEntity(String.class);

            System.out.println("Output from Server .... \n");
            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(URI).build();
    }
}