package ua.cn.stu.domain;
import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topic", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user",
                foreignKey = @ForeignKey(name = "fk_user_topic", 
                                         foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES \"user\"(id_user) ON UPDATE CASCADE ON DELETE SET NULL"))
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rubric", referencedColumnName = "id_rubric",
                foreignKey = @ForeignKey(name = "fk_rubric_topic", 
                                         foreignKeyDefinition = "FOREIGN KEY (id_rubric) REFERENCES Rubric(id_rubric) ON UPDATE CASCADE ON DELETE CASCADE"))
    private Rubric rubric;

    // Constructors
    public Topic() {}

    public Topic(String title, User author, Rubric rubric) {
        this.title = title;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        this.author = author;
        this.rubric = rubric;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Rubric getRubric() {
        return rubric;
    }

    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }
}
