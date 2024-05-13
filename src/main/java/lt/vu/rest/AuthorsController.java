package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.var;
import lt.vu.entities.Author;
import lt.vu.persistence.AuthorDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Setter
@Getter
@ApplicationScoped
@Path("/authors")
public class AuthorsController {
    @Inject
    AuthorDAO authorDAO;

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(AuthorDTO authorDTO) {
        Author newAuthor = new Author();
        newAuthor.setName(authorDTO.getName());
        try {
            authorDAO.persist(newAuthor);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getById(@PathParam("id") final Integer id) {
        Author author = authorDAO.findOne(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(author.getName()).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Integer authorId, AuthorDTO authorDTO) {
        try {
            Author author = authorDAO.findOne(authorId);
            if (author == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            author.setName(authorDTO.getName());
            authorDAO.update(author);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
