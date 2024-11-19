package ua.cn.stu.domain;
import javax.persistence.*;
@Entity
@Table(name = "Rubric",
uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Rubric {
 public Rubric() {
 }
 public Rubric(String name, String description) {
 this.name = name;
 this.description = description;
 }
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id_rubric")
 public Long id;
 @Column(name = "name", length = 100, nullable = false)
 public String name;
 @Column(name = "description", length = 255)
 public String description;
 public Long getId() {
 return id;
 }
 public void setId(Long id) {
 this.id = id;
 }
 public String getName() {
 return name;
 }
 public void setName(String name) {
 this.name = name;
 }
 public String getDescription() {
 return description;
 }public void setDescription(String description) {
	 this.description = description;
	 }
	}