package com.sforce.android.soap.partner;

import java.util.ArrayList;

import com.sforce.android.soap.partner.sobject.SObject;



public class DescribeSObjectResult {
    private ArrayList<SObject> records=new ArrayList<SObject>();
    
    private int size;
    private boolean activateable;
    private boolean createable;
    private boolean custom;
    private boolean customSetting;
    private boolean deletable;
    private boolean deprecatedAndHidden;
    private boolean feedEnabled;
    

    public DescribeSObjectResult() {
    }
    
    public DescribeSObjectResult ( ArrayList<SObject> records, QueryResult queryResult,
    		int size, boolean activateable, boolean creatable, boolean custom,
    		boolean customSetting, boolean deletable, boolean deprecatedAndHidden, boolean feedEnabled)
		{
    		this.records = records;
    		this.size = size;
    		this.activateable = activateable;
			this.createable = creatable;
			this.custom = custom;
			this.customSetting = customSetting;
			this.deletable = deletable;
			this.deprecatedAndHidden = deprecatedAndHidden;
			this.feedEnabled = feedEnabled;
		}

    
    public ArrayList<SObject> getRecords() {
        return records;
    }
    public void setRecords(ArrayList<SObject> records) {
        this.records = records;
    }

    public SObject getRecords(int i) {
        return this.records.get(i);
    }

    public void setRecords(int i, SObject sobject) {
        (this.records).add(i, sobject);
    
    }
    
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public boolean isActivateable() {
        return activateable;
    }
    public void setActivateable(boolean activateable) {
        this.activateable = activateable;
    }
    
    public boolean isCreateable() {
        return createable;
    }
    public void setCreateable(boolean createable) {
        this.createable = createable;
    }
    
    public boolean isCustom() {
        return custom;
    }
    public void setCustom(boolean custom) {
        this.custom = custom;
    }
    
    public boolean isCustomSetting() {
        return customSetting;
    }
    public void setCustomSetting(boolean customSetting) {
        this.customSetting = customSetting;
    }
    
    public boolean isDeletable() {
        return deletable;
    }
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
    
    public boolean isDeprecatedAndHidden() {
        return deprecatedAndHidden;
    }
    public void setDeprecatedAndHidden(boolean deprecatedAndHidden) {
        this.deprecatedAndHidden = deprecatedAndHidden;
    }
    
    public boolean isFeedEnabled() {
        return feedEnabled;
    }
    public void setFeedEnabled(boolean feedEnabled) {
        this.feedEnabled = feedEnabled;
    }
    
}
