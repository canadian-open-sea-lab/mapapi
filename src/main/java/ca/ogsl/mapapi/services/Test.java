package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces({"application/x-javascript"})
@Path("testservice")
public class Test{

    public Test(){}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response categoriesForTopicTest(@QueryParam("lang") String lang){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        Topic topic;
        try {
            topic = em.find(Topic.class,1);
            return Response.status(200).entity(topic).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("layers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response layersForTopicTest(@QueryParam("lang") String lang){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root =  cq.from(Layer.class);
            Join topicJoin =  root.join("topics");
            cq.where(cb.and(cb.equal(topicJoin.get("id"),1),cb.equal(root.get("isBackground"),false)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
            return Response.status(200).entity(layers).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
    @GET
    @Path("baselayers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response baseLayersForTopicTest(@QueryParam("lang") String lang){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root =  cq.from(Layer.class);
            Join topicJoin =  root.join("topics");
            cq.where(cb.and(cb.equal(topicJoin.get("id"),1),cb.equal(root.get("isBackground"),true)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
            return Response.status(200).entity(layers).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
}