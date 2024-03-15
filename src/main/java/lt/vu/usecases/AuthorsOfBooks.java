package lt.vu.usecases;

import lombok.Getter;
import lt.vu.entities.Author;
import lt.vu.entities.Book;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.BooksDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Model
@SessionScoped
public class AuthorsOfBooks implements Serializable {

    @Inject
    private BooksDAO booksDAO;

    @Inject
    private AuthorDAO authorDAO;

    private Book book;
    @Getter
    private List<Author> allAuthors;
    @Getter
    private Map<Author, Boolean> authorsCheckMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String bookId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bookId");
        if (bookId != null) {
            this.book = booksDAO.findOne(Integer.parseInt(bookId));
            this.allAuthors = authorDAO.loadAll();
            for(Author author : allAuthors) {
                authorsCheckMap.put(author, book.getAuthors().contains(author));
            }
        }
    }

    public boolean checkIfAuthorWroteBook(Author author) {
        return authorsCheckMap.getOrDefault(author, false);
    }
    @Transactional
    @LoggedInvocation
    public void saveAuthorsForBook() {
        List<Author> selectedAuthors = authorsCheckMap.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        book.setAuthors(selectedAuthors);
        booksDAO.update(book);
    }
}
