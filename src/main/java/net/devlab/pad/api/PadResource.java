package net.devlab.pad.api;

import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.MongoClient;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import lombok.extern.log4j.Log4j2;
import net.devlab.pad.model.Pad;

/**
 * 
 * @author dj0n1
 *
 */
@Log4j2
@Path("pad")
public class PadResource {

    private static MongoClient mongoClient = new MongoClient();
    private static Datastore datastore = new Morphia().map(Pad.class).createDatastore(mongoClient, "Pads");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPad(final @QueryParam("id") String id) {
        log.info("GET /pad id=" + id);
        if (StringUtils.isBlank(id)) {
            return Response.status(400, "no pad provided").build();
        }
        final Pad pad = datastore.createQuery(Pad.class)
                        .field("shaSum")
                        .equal(id)
                        .first();
        if (pad != null) {
            Query<Pad> updateQuery = datastore.createQuery(Pad.class)
                            .field("shaSum")
                            .equal(id);
            UpdateOperations<Pad> operations = datastore.createUpdateOperations(Pad.class)
                            .set("views", 1 + pad.getViews());
            datastore.update(updateQuery, operations);
            return Response.ok(pad).build();
        }
        return Response.status(400, "no such pad: " + id).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPad(final Pad pad) {
        log.info("POST /pad");
        pad.setCreationDate(LocalDateTime.now());
        if (StringUtils.isBlank(pad.getAuthor())) {
            pad.setAuthor("Anonymous");
        }
        if (StringUtils.isBlank(pad.getTitle())) {
            pad.setTitle("Pad #" + pad.getShaSum());
        }
        if (StringUtils.isBlank(pad.getContent())) {
            return Response.status(400, "pad is empty").build();
        }
        datastore.save(pad);
        return Response.ok(pad).build();
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPadList() {
        log.info("GET /pad/list");
        return Response.ok(datastore.createQuery(Pad.class).find().toList()).build();
    }
}
