package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.entities.Book;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.BooksDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.OptimisticLockException;

@Model
public class BooksOfAuthor implements Serializable {

    @Inject
    private AuthorDAO authorDAO;
    @Inject
    private BooksDAO booksDAO;

    @Getter @Setter
    private Author author;
    @Getter @Setter
    private Book bookToCreate = new Book();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParameters.isEmpty()){
            return;
        }
        Integer authorId = Integer.parseInt(requestParameters.get("authorId"));
        this.author = authorDAO.findOne(authorId);
    }

    @Transactional
    @LoggedInvocation
    public void createBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            Thread.sleep(3000);
            if (author.hasBookWithTitle(bookToCreate.getName())) {
                context.getExternalContext().getFlash().put("message", "Error: book already exists");
                return;
            }
            Book tempBook = booksDAO.findOneByName(bookToCreate.getName());
            if (tempBook == null){
                booksDAO.persist(bookToCreate);
                tempBook = bookToCreate;
            }
            else{ // OptimisticLockException handling: (comment this block to get stack trace)
                context.getExternalContext().getFlash().put("message", "OptimisticLockException");
                return;
            }
            author.createBook(tempBook);
        } catch (InterruptedException ie) {
            context.getExternalContext().getFlash().put("message", "InterruptedException");
            Thread.currentThread().interrupt();
        } catch (OptimisticLockException ole) {
            context.getExternalContext().getFlash().put("message", "OptimisticLockException");
        } catch (Exception e){
            context.getExternalContext().getFlash().put("message", "Exception: " + e.getMessage());
        }

    }
}
