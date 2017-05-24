package ca.ogsl.mapapi.resource;

import ca.ogsl.mapapi.errorhandling.AppException;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;
import ca.ogsl.mapapi.services.TopicService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("topics")
@Produces(MediaType.APPLICATION_JSON)
public class TopicResource {

    private TopicService topicService = new TopicService();

    @GET
    public Response listTopics(@QueryParam("lang") String lang) throws AppException {
        List<Topic> topics = this.topicService.listTopics(lang);
        return Response.status(200).entity(topics).build();
    }

    @GET
    @Path("{id}")
    public Response getTopicForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        Topic topic = this.topicService.getTopicForId(lang, id);
        return Response.status(200).entity(topic).build();
    }

    @GET
    @Path("getTopicForCode")
    public Response getTopicForCode(@QueryParam("lang") String lang, @QueryParam("code") String code) throws Exception {
        Topic topic = this.topicService.getTopicForCode(lang, code);
        return Response.status(200).entity(topic).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCreateTopic(Topic topic, @HeaderParam("role") String role) throws AppException {
        Topic databaseTopic = this.topicService.postCreateTopic(topic, role);
        return Response.status(201).entity(databaseTopic).build();
    }

    @GET
    @Path("getBaseLayerCatalog")
    public Response getBaseLayersForTopic(@QueryParam("lang") String lang, @QueryParam("code") String code) {
        List<Layer> layers = this.topicService.getBaseLayersForTopic(lang, code);
        return Response.status(200).entity(layers).build();
    }

    @GET
    @Path("getLayerCatalog")
    public Response getLayersForTopic(@QueryParam("lang") String lang, @QueryParam("code") String code) {
        List<Layer> layers = this.topicService.getLayersForTopic(lang, code);
        return Response.status(200).entity(layers).build();
    }
}
