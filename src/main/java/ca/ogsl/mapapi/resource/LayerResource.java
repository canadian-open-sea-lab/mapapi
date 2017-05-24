package ca.ogsl.mapapi.resource;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.services.LayerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("layers")
@Produces(MediaType.APPLICATION_JSON)
public class LayerResource {

    private LayerService layerService = new LayerService();

    @GET
    public Response listLayers(@QueryParam("lang") String lang) throws AppException {
        List<Layer> layerList = this.layerService.listLayers(lang);
        return Response.status(200).entity(layerList).build();
    }

    @GET
    @Path("{id}")
    public Response getLayerForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) throws AppException {
        Layer databaseLayer = this.layerService.getLayerForId(lang, id);
        return Response.status(200).entity(databaseLayer).build();
    }

    @GET
    @Path("getLayerForCode")
    public Response getlayerForCode(@QueryParam("lang") String lang, @QueryParam("code") String code) throws AppException {
        Layer databaseLayer = this.layerService.getlayerForCode(lang, code);
        return Response.status(200).entity(databaseLayer).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateLayer(Layer layer, @HeaderParam("role") String role) throws AppException {
        Layer databaseLayer = this.layerService.postCreateLayer(layer, role);
        return Response.status(201).entity(databaseLayer).build();
    }

    @GET
    @Path("{id}/getLayerInformation")
    @Produces(MediaType.TEXT_HTML)
    public Response getLayerInformation(@QueryParam("lang") String lang, @PathParam("id") Integer layerId) throws
            AppException {
        String htmlContent = this.layerService.getLayerInformation(lang, layerId);
        return Response.status(200).entity(htmlContent).build();
    }
}
