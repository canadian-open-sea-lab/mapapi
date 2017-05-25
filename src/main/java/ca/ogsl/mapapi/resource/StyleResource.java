package ca.ogsl.mapapi.resource;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Style;
import ca.ogsl.mapapi.services.StyleService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("styles")
@Produces(MediaType.APPLICATION_JSON)
public class StyleResource {

    private StyleService styleService = new StyleService();

    @GET
    public Response listStyles(@QueryParam("lang") String lang) throws AppException {
        List<Style> styleList = this.styleService.listStyles(lang);
        return Response.status(200).entity(styleList).build();
    }

    @GET
    @Path("{id}")
    public Response getStyleForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) throws AppException {
        Style style = this.styleService.getStyleForId(lang, id);
        return Response.status(200).entity(style).build();
    }
}
