package ua.cn.stu.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ua.cn.stu.dao.HibernateDAOFactory;
import ua.cn.stu.domain.User;
import java.time.format.DateTimeFormatter;

@Path("user")
public class UserService {
	

	public class UserJSON {
	    private Long id;
	    private String username;
	    private String email;
	    private String registrationDate;  // As a string for JSON
	    private Integer messagesCount;

	    // Constructor that accepts a User object and converts registrationDate to String
	    public UserJSON(User user) {
	        this.id = user.getId();
	        this.username = user.getUsername();
	        this.email = user.getEmail();
	        // Convert registrationDate to a formatted string
	        this.registrationDate = user.getRegistrationDate().toLocalDateTime()
	                                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	        this.messagesCount = user.getMessagesCount();
	    }

		public Long getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getEmail() {
			return email;
		}

		public String getRegistrationDate() {
			return registrationDate;
		}

		public Integer getMessagesCount() {
			return messagesCount;
		}}


    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserJSON> getAll() {
        return HibernateDAOFactory
        		.getInstance()
        		.getUserDAO()
        		.findAll()
        		.stream()
        		.map(UserJSON::new)
        		.collect(Collectors.toList());
    }

    @PUT
    @Path("add/{username}/{email}")
    public void add(@PathParam("username") String username, @PathParam("email") String email) {
        User user = new User(username, email);
        HibernateDAOFactory.getInstance().getUserDAO().create(user);
    }

    @DELETE
    @Path("delete/{id}")
    public void delete(@PathParam("id") String id) {
        Long idAsLong = Long.parseLong(id);
        HibernateDAOFactory.getInstance().getUserDAO().delete(idAsLong);
    }

    @POST
//    @Path("update/{id}/{username}/{email}/{messagesCount}")
    @Path("update/{id}/{username}/{email}")
    public void update(
        @PathParam("id") String id,
        @PathParam("username") String username,
        @PathParam("email") String email
//        @PathParam("messagesCount") String messagesCount
        ) {
        
        Long idAsLong = Long.parseLong(id);
        User user = HibernateDAOFactory.getInstance().getUserDAO().findById(idAsLong);
        
        user.setUsername(username);
        user.setEmail(email);
//        user.setMessagesCount(Integer.parseInt(messagesCount)); // Assuming messagesCount can be parsed to an Integer

        HibernateDAOFactory.getInstance().getUserDAO().update(user);
    }
}
