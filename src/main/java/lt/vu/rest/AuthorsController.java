package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.persistence.AuthorDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
