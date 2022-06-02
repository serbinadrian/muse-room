package com.koley.musrights.domains;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Rent")
@EnableAutoConfiguration
public class UserRent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long userId;
    long compositionId;
    int listenCount;

    Date rentDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(long compositionId) {
        this.compositionId = compositionId;
    }

    public int getListenCount() {
        return listenCount;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }
}
