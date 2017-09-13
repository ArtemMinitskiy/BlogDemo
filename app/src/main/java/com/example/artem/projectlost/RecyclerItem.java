package com.example.artem.projectlost;

public class RecyclerItem {

    private String name;
    private String describe;
    private String urlImAccount;
    private String urlImage;

    public RecyclerItem(String name, String describe) {
        this.name = name;
        this.describe = describe;
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
}