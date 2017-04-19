package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.filter.AuthenticationFilter;
import ca.ogsl.mapapi.models.Layer;
import ca.ogsl.mapapi.models.Topic;
import ca.ogsl.mapapi.util.GenericsUtil;
import org.hibernate.transform.DistinctResultTransformer;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
            Join categoryJoin = (Join) root.fetch("root", JoinType.LEFT);
            cq.where(cb.equal(root.get("code"), code));
            TypedQuery<Topic> tq = em.createQuery(cq);
            topic = GenericsUtil.getSingleResultOrNull(tq);
            GenericsUtil gu = new GenericsUtil();
            Topic finalTopic = gu.recursiveInitialize(topic, Topic.class);
            return Response.status(200).entity(topic).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Response.status(500).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response topics(Topic topic, @HeaderParam("role") String role) {
        if (role.equals(AuthenticationFilter.ADMIN_ROLE)) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(topic);
            et.commit();
            return Response.status(200).entity(topic).build();
        }
        else{
            return Response.status(403).build();
        }
    }

    @GET
    @Path("{code}/BaseLayerCatalog")
    public Response getBaseLayersForTopic(@QueryParam("lang") String lang, @PathParam("code") String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join topicJoin = root.join("topics", JoinType.LEFT);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.and(cb.equal(topicJoin.get("code"), code), cb.equal(root.get("isBackground"), true)));
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
    public Response getLayersForTopic(@QueryParam("lang") String lang, @PathParam("code") String code) {
        PersistenceManager.setLanguageContext(lang);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        List<Layer> layers;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
            Root<Layer> root = cq.from(Layer.class);
            Join topicJoin = root.join("topics", JoinType.LEFT);
            Join legendsJoin = (Join) root.fetch("legends", JoinType.LEFT);
            Join sourceJoin = (Join) root.fetch("source", JoinType.LEFT);
            Join urlParamJoin = (Join) sourceJoin.fetch("urlParams", JoinType.LEFT);
            cq.where(cb.and(cb.equal(topicJoin.get("code"), code), cb.equal(root.get("isBackground"), false)));
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
