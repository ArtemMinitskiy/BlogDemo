package com.example.artem.projectlost;

public class CardItem {

    private String name;
    private String describe;
    private String urlImAccount;
    private String urlImage;
    private String Uid;

    public CardItem() {

    }

    public CardItem(String name) {
        this.name = name;
    }

    public CardItem(String name, String describe, String urlImAccount, String urlImage, String uid) {
        this.name = name;
        this.describe = describe;
        this.urlImAccount = urlImAccount;
        this.urlImage = urlImage;
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrlImAccount() {
        return urlImAccount;
    }

    public void setUrlImAccount(String urlImAccount) {
        this.urlImAccount = urlImAccount;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}