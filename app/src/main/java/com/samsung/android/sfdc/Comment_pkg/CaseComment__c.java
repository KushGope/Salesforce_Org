package com.samsung.android.sfdc.Comment_pkg;

import com.samsung.android.sfdc.Case_pkg.Attributes;

public class CaseComment__c {

    private Attributes attributes;
    private String CommentBody__c;
    private String FeedItemId__c;
    private String CreatedDate;

    public String getCommentBody__c() {
        return CommentBody__c;
    }

    public void setCommentBody__c(String commentBody__c) {
        CommentBody__c = commentBody__c;
    }

    public String getFeedItemId__c() {
        return FeedItemId__c;
    }

    public void setFeedItemId__c(String feedItemId__c) {
        FeedItemId__c = feedItemId__c;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
