package com.koley.musrights.domains;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Compositions")
@EnableAutoConfiguration
public class Composition {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    long id;
    long ownerId;
    String name;
    String author;
    Date uploadDate;
    boolean isFake = false;
    //todo duration, ?filename

    public Composition(String compositionName, String compositionAuthor, User user){
        this.setName(compositionName);
        this.setAuthor(compositionAuthor);
        this.setOwnerId(user.getId());
        this.setFake(true);
        Date date = new Date();
        System.out.println("date is: " + date);
        this.setUploadDate(date);
    }

    public Composition() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }
}
