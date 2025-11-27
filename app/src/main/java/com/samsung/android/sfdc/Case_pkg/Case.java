package com.samsung.android.sfdc.Case_pkg;

public class Case {

    private Attributes attributes;
    private String CaseNumber;
    private String Description;
    private String Subject;
    private String Category__c;
    private String CaseType__c;
    private Boolean IsClosed;
    private OwnerResponse Owner;

    public String getQueueGsmeAssignDateFromCaseKPI__c() {
        return QueueGsmeAssignDateFromCaseKPI__c;
    }

    public void setQueueGsmeAssignDateFromCaseKPI__c(String queueGsmeAssignDateFromCaseKPI__c) {
        QueueGsmeAssignDateFromCaseKPI__c = queueGsmeAssignDateFromCaseKPI__c;
    }

    private String QueueGsmeAssignDateFromCaseKPI__c;

    public String getOwnerL3__c() {
        return OwnerL3__c;
    }

    public void setOwnerL3__c(String ownerL3__c) {
        OwnerL3__c = ownerL3__c;
    }

    private String OwnerL3__c;
    private String QueueL3MainManager__c;
    private String ClosedDate;


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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getCategory__c() {
        return Category__c;
    }

    public void setCategory__c(String category__c) {
        Category__c = category__c;
    }

    public String getCaseType__c() {
        return CaseType__c;
    }

    public void setCaseType__c(String caseType__c) {
        CaseType__c = caseType__c;
    }

    public Boolean getClosed() {
        return IsClosed;
    }

    public void setClosed(Boolean closed) {
        IsClosed = closed;
    }

    public OwnerResponse getOwner() {
        return Owner;
    }

    public void setOwner(OwnerResponse ownerResponse) {
        this.Owner = ownerResponse;
    }

}
