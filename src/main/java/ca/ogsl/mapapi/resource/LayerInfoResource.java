package ca.ogsl.mapapi.resource;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.LayerInfo;
import ca.ogsl.mapapi.services.LayerInfoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("layerInfos")
@Produces(MediaType.APPLICATION_JSON)
public class LayerInfoResource {

    private LayerInfoService layerInfoService = new LayerInfoService();

    @GET
    public Response listLayerInfos(@QueryParam("lang") String lang) {
        List<LayerInfo> layerInfos = this.layerInfoService.listLayerInfos(lang);
        return Response.status(200).entity(layerInfos).build();
    }
    @GET
    @Path("{id}")
    public Response getLayerInfoForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        LayerInfo layerInfo = this.layerInfoService.getLayerInfoForId(lang, id);
        return Response.status(200).entity(layerInfo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateLayerInfo(LayerInfo layerInfo, @HeaderParam("role") String role) throws AppException {
        LayerInfo databaseLayerInfo = this.layerInfoService.postCreateLayerInfo(layerInfo, role);
        return Response.status(201).entity(databaseLayerInfo).build();
    }

    @POST
    @Path("postCreateMultipleLayerInfos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateMultipleLayerInfos(List<LayerInfo> layerInfos, @HeaderParam("role") String role) throws
            AppException {
        List<LayerInfo> databaseLayerInfos = this.layerInfoService.postCreateMultipleLayerInfos(layerInfos, role);
        return Response.status(201).entity(databaseLayerInfos).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteLayerInfoForId(@PathParam("id") Integer id, @HeaderParam("role") String role) throws
            AppException {
        this.layerInfoService.deleteLayerInfoForId(id, role);
        return Response.status(204).build();
    }
}
