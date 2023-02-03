package br.com.dea.management.student.domain;

import br.com.dea.management.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String university;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getUniversity() {
        return university;
    }

    public String getGraduation() {
        return graduation;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public User getUser() {
        return user;
    }

    @Column
    private String graduation;

    @Column
    private LocalDate finishDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
