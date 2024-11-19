package ua.cn.stu.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ua.cn.stu.dao.HibernateDAOFactory;
import ua.cn.stu.domain.Topic;
import ua.cn.stu.domain.User;
import ua.cn.stu.service.UserService.UserJSON;
import ua.cn.stu.domain.Rubric;

@Path("topic")
public class TopicService {
	public static class TopicJSON {
	    private Long id;
	    private String title;
	    private String createdAt;
	    private String updatedAt;
	    private Long authorId;
	    private Long rubricId;

	    // Date formatter for converting LocalDateTime to String
	    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    // Constructor that accepts a Topic object
	    public TopicJSON(Topic topic) {
	        this.id = topic.getId();
	        this.title = topic.getTitle();
	        this.createdAt = topic.getCreatedAt().toLocalDateTime().format(formatter);
	        this.updatedAt = topic.getUpdatedAt().toLocalDateTime().format(formatter);
	        this.authorId = topic.getAuthor() != null ? topic.getAuthor().getId() : null;
	        this.rubricId = topic.getRubric() != null ? topic.getRubric().getId() : null;
	    }

		public Long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public String getUpdatedAt() {
			return updatedAt;
		}

		public Long getAuthorId() {
			return authorId;
		}

		public Long getRubricId() {
			return rubricId;
		}}
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TopicJSON> getAll() {
        return HibernateDAOFactory
        		.getInstance()
        		.getTopicDAO()
        		.findAll()
        		.stream()
        		.map(TopicJSON::new)
        		.collect(Collectors.toList());
    }

    @PUT
    @Path("add/{title}/{authorId}/{rubricId}")
    public void add(
            @PathParam("title") String title,
            @PathParam("authorId") String authorId,
            @PathParam("rubricId") String rubricId) {
        
        Long authorIdLong = Long.parseLong(authorId);
        Long rubricIdLong = Long.parseLong(rubricId);
        
        User author = HibernateDAOFactory.getInstance().getUserDAO().findById(authorIdLong);
        Rubric rubric = HibernateDAOFactory.getInstance().getRubricDAO().findById(rubricIdLong);
        
        Topic topic = new Topic(title, author, rubric);
        HibernateDAOFactory.getInstance().getTopicDAO().create(topic);
    }

    @DELETE
    @Path("delete/{id}")
    public void delete(@PathParam("id") String id) {
        Long idAsLong = Long.parseLong(id);
        HibernateDAOFactory.getInstance().getTopicDAO().delete(idAsLong);
    }

    @POST
    @Path("update/{id}/{title}/{authorId}/{rubricId}")
    public void update(
            @PathParam("id") String id,
            @PathParam("title") String title,
            @PathParam("authorId") String authorId,
            @PathParam("rubricId") String rubricId) {
        
        Long idAsLong = Long.parseLong(id);
        Topic topic = HibernateDAOFactory.getInstance().getTopicDAO().findById(idAsLong);
        
        User author = HibernateDAOFactory.getInstance().getUserDAO().findById(Long.parseLong(authorId));
        Rubric rubric = HibernateDAOFactory.getInstance().getRubricDAO().findById(Long.parseLong(rubricId));
        
        topic.setTitle(title);
        topic.setAuthor(author);
        topic.setRubric(rubric);
        topic.setUpdatedAt(LocalDateTime.now()); // Update the timestamp on modification
        
        HibernateDAOFactory.getInstance().getTopicDAO().update(topic);
    }
}
