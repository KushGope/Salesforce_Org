package com.samsung.android.sfdc.Case_pkg;

public class Record {
    private Attributes attributes;
    private String CaseNumber;
    private String Id;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.CaseNumber = caseNumber;
    }

    public String getId() {return Id;}

    public void setId(String id) {Id = id;}
}
