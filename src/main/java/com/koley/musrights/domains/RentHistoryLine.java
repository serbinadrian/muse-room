package com.koley.musrights.domains;

import java.util.Date;

public class RentHistoryLine {

    long userId;
    long compositionId;
    Date lineDate;

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

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }
}
