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

    Genres genre;
    boolean isFake = false;
    boolean isFirstOwner = false;

    int durationInSeconds;

    String formatedDuration;

    String filePath;

    boolean isAvailableToBuy = true;

    int rentTimes;
    int  buyTimes;

    public Composition(String compositionName, String compositionAuthor, User user){
        this.setName(compositionName);
        this.setAuthor(compositionAuthor);
        this.setOwnerId(user.getId());
        this.setFake(true);

        Date date = new Date();
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

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getFormatedDuration() {
        return formatedDuration;
    }

    public void setFormatedDuration(String formatedDuration) {
        this.formatedDuration = formatedDuration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isAvailableToBuy() {
        return isAvailableToBuy;
    }

    public void setAvailableToBuy(boolean availableToBuy) {
        isAvailableToBuy = availableToBuy;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public int getRentTimes() {
        return rentTimes;
    }

    public void setRentTimes(int rentTimes) {
        this.rentTimes = rentTimes;
    }

    public int getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(int buyTimes) {
        this.buyTimes = buyTimes;
    }

    public boolean isFirstOwner() {
        return isFirstOwner;
    }

    public void setFirstOwner(boolean firstOwner) {
        isFirstOwner = firstOwner;
    }
}
