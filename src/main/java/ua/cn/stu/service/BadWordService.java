package ua.cn.stu.service;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ua.cn.stu.dao.HibernateDAOFactory;
import ua.cn.stu.domain.BadWord;

@Path("badword")
public class BadWordService {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BadWord> getAll() {
        return HibernateDAOFactory.getInstance().getBadWordDAO().findAll();
    }

    @PUT
    @Path("add/{word}")
    public void add(@PathParam("word") String word) {
        BadWord badWord = new BadWord(word);
        HibernateDAOFactory.getInstance().getBadWordDAO().create(badWord);
    }

    @DELETE
    @Path("delete/{id}")
    public void delete(@PathParam("id") String id) {
        Long idAsLong = Long.parseLong(id);
        BadWord badWord = HibernateDAOFactory.getInstance().getBadWordDAO().findById(idAsLong);
        HibernateDAOFactory.getInstance().getBadWordDAO().delete(badWord);
    }

    @POST
    @Path("update/{id}/{word}")
    public void update(@PathParam("id") String id, @PathParam("word") String word) {
        Long idAsLong = Long.parseLong(id);
        BadWord badWord = HibernateDAOFactory.getInstance().getBadWordDAO().findById(idAsLong);
        badWord.setWord(word);
        HibernateDAOFactory.getInstance().getBadWordDAO().update(badWord);
    }
}
