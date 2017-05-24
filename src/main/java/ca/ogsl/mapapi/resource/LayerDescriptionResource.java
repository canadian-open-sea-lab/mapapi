package ca.ogsl.mapapi.resource;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerDescription;
import ca.ogsl.mapapi.services.LayerDescriptionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("layerDescriptions")
@Produces(MediaType.APPLICATION_JSON)
public class LayerDescriptionResource {

    private LayerDescriptionService layerDescriptionService = new LayerDescriptionService();

    @GET
    public Response listLayerDescriptions(@QueryParam("lang") String lang) throws AppException {
        List<LayerDescription> layerDescriptions = this.layerDescriptionService.listLayerDescriptions(lang);
        return Response.status(200).entity(layerDescriptions).build();
    }

    @GET
    @Path("{id}")
    public Response getLayerDescriptionForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) throws
            AppException {
        LayerDescription layerDescription = this.layerDescriptionService.getLayerDescriptionForId(lang, id);
        return Response.status(200).entity(layerDescription).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteLayerDescriptionForId(@PathParam("id") Integer id,
                                                @HeaderParam("role") String role) throws AppException {
        this.layerDescriptionService.deleteLayerDescriptionForId(id, role);
        return Response.status(204).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateLayerDescription(LayerDescription layerDescription, @HeaderParam("role") String role)
            throws AppException {
        LayerDescription databaseLayerDescription = this.layerDescriptionService.postCreateLayerDescription
                (layerDescription, role);
        return Response.status(201).entity(databaseLayerDescription).build();
    }
}
