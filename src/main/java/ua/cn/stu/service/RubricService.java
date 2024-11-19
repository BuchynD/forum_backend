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
import ua.cn.stu.domain.*;
@Path("rubric")
public class RubricService {
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rubric> getAll() {
		return HibernateDAOFactory.getInstance().getRubricDAO().findAll();
	}
	@PUT
	@Path("add/{name}/{description}")
	public void add(@PathParam("name") String name,
			@PathParam("description") String description) {
		Rubric rubric = new Rubric(name, description);
		HibernateDAOFactory.getInstance().getRubricDAO().create(rubric);
	}
	@DELETE
	@Path("delete/{id}")
	public void delete(@PathParam("id") String id) {
		Long idAsLong = Long.parseLong(id);
		HibernateDAOFactory.getInstance().getRubricDAO().delete(idAsLong);
	}
	@POST

	@Path("update/{id}/{name}/{description}")
	public void update(@PathParam("id") String id,
			@PathParam("name") String name,
			@PathParam("description") String description) {
		Long idAsLong = Long.parseLong(id);
		Rubric rubric = HibernateDAOFactory.getInstance().getRubricDAO().findById(idAsLong);
		rubric.setName(name);
		rubric.setDescription(description);
		HibernateDAOFactory.getInstance().getRubricDAO().update(rubric);
	}
}
