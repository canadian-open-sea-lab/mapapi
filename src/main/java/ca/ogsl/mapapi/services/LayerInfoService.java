package ca.ogsl.mapapi.services;

import ca.ogsl.mapapi.models.LayerInfo;

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
import java.util.List;

@SuppressWarnings("Duplicates")
@Path("layerInfo")
@Produces(MediaType.APPLICATION_JSON)
public class LayerInfoService {

    public LayerInfoService() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response layerInfos(List<LayerInfo> layerInfos) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mapapi");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        for (LayerInfo layerInfo:layerInfos){
            em.merge(layerInfo);
        }
        et.commit();
        return Response.status(200).entity(layerInfos).build();
    }

}
