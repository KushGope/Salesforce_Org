package com.samsung.android.sfdc.Comment_pkg;

import java.util.ArrayList;
import java.util.List;

public class CommentResponse {

    private Integer totalSize;
    private Boolean done;
    private List<CaseComment__c> records = new ArrayList<>();

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public List<CaseComment__c> getRecords() {
        return records;
    }

    public void setRecords(List<CaseComment__c> records) {
        this.records = records;
    }
}
