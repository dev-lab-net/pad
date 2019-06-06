package net.devlab.pad.api;

import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
import net.devlab.pad.util.PadUtils;

/**
 * 
 * @author dj0n1
 *
 */
@Log4j2
@Path("/api/v1/pad")
public class PadResource {

    private static MongoClient mongoClient = new MongoClient();
    private static Datastore datastore = new Morphia().map(Pad.class).createDatastore(mongoClient, "Pads");

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPad(final @PathParam("id") String id) {
        log.info("GET /api/v1/pad id={}", id);
        if (StringUtils.isBlank(id)) {
            return Response.status(400, "No pad provided").build();
        }
        final Query<Pad> query = datastore.createQuery(Pad.class)
                .field("hash")
                .startsWith(id);
        final Pad pad = query.first();
        if (pad != null) {
            UpdateOperations<Pad> operations = datastore.createUpdateOperations(Pad.class)
                    .set("views", 1 + pad.getViews());
            datastore.update(query, operations);
            // give generic title
            if (!PadUtils.isTitled(pad)) {
                pad.setTitle(PadUtils.getGenericTitle(pad));
            }
            return Response.ok(pad).build();
        }
        return Response.status(400, "Not Found: " + id).build();
    }

    @POST
    @Path("/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJsonPad(final Pad pad) {
        log.info("POST /api/v1/pad/json");
        pad.setCreationDate(LocalDateTime.now());
        if (StringUtils.isBlank(pad.getAuthor())) {
            pad.setAuthor("Anonymous");
        }
        if (StringUtils.isBlank(pad.getContent())) {
            return Response.status(400, "Pad is empty").build();
        }
        pad.computeHash();
        datastore.save(pad);
        return Response.ok(pad).build();
    }

    @POST
    @Path("/form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFormDataPad(@FormParam("author") String author,
            @FormParam("title") String title,
            @FormParam("content") String content,
            @FormParam("highlight") String highlight) {
        log.info("POST /api/v1/pad/form");
        if (StringUtils.isBlank(author)) {
            author = "Anonymous";
        }
        if (StringUtils.isBlank(content)) {
            return Response.status(400, "Pad is empty").build();
        }
        final Pad pad = Pad.builder()
                .author(author)
                .title(title)
                .content(content)
                .highlight(highlight)
                .creationDate(LocalDateTime.now()).build();
        pad.computeHash();
        datastore.save(pad);
        return Response.ok(pad).build();
    }

    @POST
    @Path("/{id}/delete")
    public Response deletePad(final @PathParam("id") String id) {
        log.info("POST api//v1/pad/{}/delete", id);
        Query<Pad> deleteQuery = datastore.createQuery(Pad.class)
                .field("hash")
                .startsWith(id);
        datastore.delete(deleteQuery);
        return Response.ok().build();
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPadList() {
        log.info("GET /api/v1/pad/list");
        return Response.ok(datastore.createQuery(Pad.class).find().toList()).build();
    }
}
