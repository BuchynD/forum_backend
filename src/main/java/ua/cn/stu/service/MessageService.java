package ua.cn.stu.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import java.time.format.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ua.cn.stu.dao.HibernateDAOFactory;
import ua.cn.stu.domain.Message;
import ua.cn.stu.domain.Topic;
import ua.cn.stu.domain.User;
import ua.cn.stu.service.TopicService.TopicJSON;

@Path("message")
public class MessageService {
	public static class MessageJSON {
	    private Long id;
	    private String content;
	    private String createdAt;
	    private String updatedAt;
	    private String status;
	    private Long topicId;
	    private Long authorId;

	    // Date formatter for converting LocalDateTime to String
	    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    // Constructor that accepts a Message object
	    public MessageJSON(Message message) {
	        this.id = message.getId();
	        this.content = message.getContent();
	        this.createdAt = message.getCreatedAt().toLocalDateTime().format(formatter);
	        this.updatedAt = message.getUpdatedAt().toLocalDateTime().format(formatter);
	        this.status = message.getStatus().name();
	        this.topicId = message.getTopic() != null ? message.getTopic().getId() : null;
	        this.authorId = message.getAuthor() != null ? message.getAuthor().getId() : null;
	    }

		public Long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public String getUpdatedAt() {
			return updatedAt;
		}

		public String getStatus() {
			return status;
		}

		public Long getTopicId() {
			return topicId;
		}

		public Long getAuthorId() {
			return authorId;
		}}
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageJSON> getAll() {
    	return HibernateDAOFactory
        		.getInstance()
        		.getMessageDAO()
        		.findAll()
        		.stream()
        		.map(MessageJSON::new)
        		.collect(Collectors.toList());
    }

    @PUT
    @Path("add/{content}/{topicId}/{authorId}/{status}")
    public void add(
            @PathParam("content") String content,
            @PathParam("topicId") String topicId,
            @PathParam("authorId") String authorId,
            @PathParam("status") String status) {
        
        Long topicIdLong = Long.parseLong(topicId);
        Long authorIdLong = Long.parseLong(authorId);
        
        Topic topic = HibernateDAOFactory.getInstance().getTopicDAO().findById(topicIdLong);
        User author = HibernateDAOFactory.getInstance().getUserDAO().findById(authorIdLong);
        
        Message message = new Message(content, topic, author, status);
        HibernateDAOFactory.getInstance().getMessageDAO().create(message);
    }

    @DELETE
    @Path("delete/{id}")
    public void delete(@PathParam("id") String id) {
        Long idAsLong = Long.parseLong(id);
        HibernateDAOFactory.getInstance().getMessageDAO().delete(idAsLong);
    }

    @POST
    @Path("update/{id}/{content}/{topicId}/{authorId}/{status}")
    public void update(
            @PathParam("id") String id,
            @PathParam("content") String content,
            @PathParam("topicId") String topicId,
            @PathParam("authorId") String authorId,
            @PathParam("status") String status) {
        
        Long idAsLong = Long.parseLong(id);
        Message message = HibernateDAOFactory.getInstance().getMessageDAO().findById(idAsLong);
        
        Topic topic = HibernateDAOFactory.getInstance().getTopicDAO().findById(Long.parseLong(topicId));
        User author = HibernateDAOFactory.getInstance().getUserDAO().findById(Long.parseLong(authorId));
        
        message.setContent(content);
        message.setTopic(topic);
        message.setAuthor(author);
        message.setStatus(Message.Status.valueOf(status));
        message.setUpdatedAt(LocalDateTime.now()); // Update the timestamp on modification
        
        HibernateDAOFactory.getInstance().getMessageDAO().update(message);
    }
}
