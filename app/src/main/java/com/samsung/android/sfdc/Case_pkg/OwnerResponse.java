package com.samsung.android.sfdc.Case_pkg;

public class OwnerResponse {

    //private Attributes_ attributes;
    private String Name;
    private String Alias;

    private Attributes attributes;
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }
}
