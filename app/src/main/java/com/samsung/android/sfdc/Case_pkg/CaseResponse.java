package com.samsung.android.sfdc.Case_pkg;

import java.util.ArrayList;
import java.util.List;

public class CaseResponse {
    private Integer totalSize;
    private Boolean done;
    private List<Case> records = new ArrayList<>();

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

    public List<Case> getRecords() {
        return records;
    }

    public void setRecords(List<Case> records) {
        this.records = records;
    }
}
