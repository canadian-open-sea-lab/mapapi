package ca.ogsl.mapapi.filter;

import ca.ogsl.mapapi.util.AppConstants;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    public AuthenticationFilter() {
    }

    public void filter(ContainerRequestContext requestContext) {
        String apiKey = requestContext.getHeaderString("API-KEY");
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        headers.putSingle("role", AppConstants.DEFAULT_ROLE);
        if (apiKey != null) {
            try {
                Configurations configs = new Configurations();
                XMLConfiguration xml = configs.xml("authentication.xml");
                if (apiKey.equals(xml.getString("apikey"))) {
                    headers.putSingle("role", AppConstants.ADMIN_ROLE);
                }
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
}
