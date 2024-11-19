package ua.cn.stu.domain;
import javax.persistence.*;

@Entity
@Table(name = "BadWord")
public class BadWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_word", nullable = false, unique = true)
    private Long id;

    @Column(name = "word", length = 50, nullable = false, unique = true)
    private String word;

    // Constructors
    public BadWord() {}

    public BadWord(String word) {
        this.word = word;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
