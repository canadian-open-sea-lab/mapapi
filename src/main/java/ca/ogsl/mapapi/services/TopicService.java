package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.criterion.Distinct;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by desjardisna on 2017-02-28.
 */
@SuppressWarnings("Duplicates")
@Path("topic")
@Produces(MediaType.APPLICATION_JSON)
public class TopicService {

    public TopicService() {
    }

    @GET
    public Response listTopics(@QueryParam("lang") String lang) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<Topic> topics;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
            Root<Topic> root = cq.from(Topic.class);
            TypedQuery<Topic> tq = em.createQuery(cq);
            topics = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(topics)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("{id}")
    public Response getTopicForId(@QueryParam("lang") String lang, @PathParam("id") Integer id) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        Topic topic;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
            Root<Topic> root = cq.from(Topic.class);
            cq.where(cb.equal(root.get("id"), id));
            TypedQuery<Topic> tq = em.createQuery(cq);
            topic = GenericsUtil.getSingleResultOrNull(tq);
            return Response.status(200).entity(topic).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("getTopicForCode")
    public Response getTopicForCode(@QueryParam("lang") String lang, @QueryParam("code") String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        Topic topic;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
            Root<Topic> root = cq.from(Topic.class);
            Join categoryJoin = (Join) root.fetch("root");
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<Topic> tq = em.createQuery(cq);
            topic = GenericsUtil.getSingleResultOrNull(tq);
            GenericsUtil gu= new GenericsUtil();
            Topic finalTopic=gu.recursiveInitialize(topic, Topic.class);
            return Response.status(200).entity(topic).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
    @GET
    @Path("{code}/BaseLayerCatalog")
    public Response getBaseLayersForTopic(@QueryParam("lang") String lang, @PathParam("code") String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root =  cq.from(Layer.class);
            Join topicJoin =  root.join("topics");
            Join sourceJoin = (Join)root.fetch("source");
            cq.where(cb.and(cb.equal(topicJoin.get("code"),code),cb.equal(root.get("isBackground"),true)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(layers)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @GET
    @Path("{code}/LayerCatalog")
    public Response getLayersForTopic(@QueryParam("lang") String lang, @PathParam("code") String code){
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("mapapi");
        EntityManager em= emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root =  cq.from(Layer.class);
            Join topicJoin =  root.join("topics");
            Join legendsJoin = (Join)root.fetch("legends");
            Join sourceJoin = (Join)root.fetch("source");
            cq.where(cb.and(cb.equal(topicJoin.get("code"),code),cb.equal(root.get("isBackground"),false)));
            TypedQuery<Layer> tq = em.createQuery(cq);
            layers = tq.getResultList();
            return Response.status(200).entity(DistinctResultTransformer.INSTANCE.transformList(layers)).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }
}
