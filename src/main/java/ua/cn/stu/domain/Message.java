package ua.cn.stu.domain;
import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message", nullable = false, unique = true)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_topic", referencedColumnName = "id_topic",
                foreignKey = @ForeignKey(name = "fk_topic_message", 
                                         foreignKeyDefinition = "FOREIGN KEY (id_topic) REFERENCES Topic(id_topic) ON UPDATE CASCADE ON DELETE CASCADE"))
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user",
                foreignKey = @ForeignKey(name = "fk_user_message", 
                                         foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES \"user\"(id_user) ON UPDATE CASCADE ON DELETE SET NULL"))
    private User author;

    // Enum for status values
    public enum Status {
        active, archived, deleted
    }

    // Constructors
    public Message() {}

    public Message(String content, Topic topic, User author, String status) {
        this.content = content;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        this.topic = topic;
        this.author = author;
        this.status = Status.valueOf(status);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) throws IllegalArgumentException {
    	if (updatedAt.before(Timestamp.valueOf(createdAt))) {
            throw new IllegalArgumentException("updatedAt cannot be before createdAt");
        }
        this.createdAt = Timestamp.valueOf(createdAt);
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) throws IllegalArgumentException {
    	if (updatedAt.isBefore(createdAt.toLocalDateTime())) {
            throw new IllegalArgumentException("updatedAt cannot be before createdAt");
        }
        this.updatedAt = Timestamp.valueOf(updatedAt);
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
