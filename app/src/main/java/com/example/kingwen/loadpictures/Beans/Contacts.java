package com.example.kingwen.loadpictures.Beans;

import java.io.Serializable;

/**
 * Created by kingwen on 2016/3/30.
 */
public class Contacts implements Serializable {
    //姓名
     private  String contactsName;
    //电话

    public Contacts(String contactsName, String contactNumber, String contactImg) {
        this.contactsName = contactsName;
        this.contactNumber = contactNumber;
        this.contactImg = contactImg;
    }

    private  String contactNumber;
    //照片url（暂时先不考虑）先使用默认图片，等后期在数据库中进行保存之后再进行使用
    private String contactImg;

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactImg() {
        return contactImg;
    }

    public void setContactImg(String contactImg) {
        this.contactImg = contactImg;
    }
}
