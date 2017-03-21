package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.models.LayerDescription;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("Duplicates")
@Path("layerDescription")
@Produces(MediaType.APPLICATION_JSON)
public class LayerDescriptionService {

    public LayerDescriptionService() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response layerDescriptions(LayerDescription layerDescription) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(layerDescription);
        et.commit();
        return Response.status(200).entity(layerDescription).build();
    }
}
