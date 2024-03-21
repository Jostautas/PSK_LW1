package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.AuthorMapper;
import lt.vu.mybatis.model.AuthorMybatis;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class AuthorsMybatis {
    @Inject
    private AuthorMapper authorMapper;

    @Getter
    private List<AuthorMybatis> allAuthors;

    @PostConstruct
    public void init(){
        this.loadAllAuthors();
    }

    private void loadAllAuthors(){
        this.allAuthors = authorMapper.selectAll();
    }
}
