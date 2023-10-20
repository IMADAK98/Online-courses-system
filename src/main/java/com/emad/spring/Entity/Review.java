package com.emad.spring.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH}
    )
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;


    public Review(String comment, Course course) {
        this.comment = comment;
        this.course = course;
    }

    public Review() {

    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", course=" + course +
                '}';
    }
}
