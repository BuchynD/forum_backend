package ua.cn.stu.domain;
import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "registration_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp registrationDate;

    @Column(name = "messages_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0 CHECK (messages_count BETWEEN 0 AND 1000)")
    private Integer messagesCount = 0;

    // Constructors
    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.registrationDate = Timestamp.valueOf(LocalDateTime.now());
        setMessagesCount(0);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate =Timestamp.valueOf(registrationDate);
    }

    public Integer getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(Integer messagesCount) throws IllegalArgumentException {
        if (messagesCount < 0 || messagesCount > 1000) {
            throw new IllegalArgumentException("messagesCount must be between 0 and 1000");
        }
        this.messagesCount = messagesCount;
    }
}
