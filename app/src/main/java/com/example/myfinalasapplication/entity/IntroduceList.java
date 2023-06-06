package com.example.myfinalasapplication.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class IntroduceList implements Serializable {
    private Integer introId;
    private String introName;
    private String description;
    private String introCode;
    private String introAvatar;

    private Timestamp joinTime;

    public IntroduceList(Integer introId, String introName, String description, String introCode, String introAvatar) {
        this.introId = introId;
        this.introName = introName;
        this.description = description;
        this.introCode = introCode;
        this.introAvatar = introAvatar;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getIntroId() {
        return introId;
    }

    public String getDescription() {
        return description;
    }

    public String getIntroAvatar() {
        return introAvatar;
    }

    public String getIntroCode() {
        return introCode;
    }

    public String getIntroName() {
        return introName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIntroAvatar(String introAvatar) {
        this.introAvatar = introAvatar;
    }

    public void setIntroCode(String introCode) {
        this.introCode = introCode;
    }

    public void setIntroId(Integer introId) {
        this.introId = introId;
    }

    public void setIntroName(String introName) {
        this.introName = introName;
    }

}
