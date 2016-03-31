package com.example.kingwen.loadpictures.Config;

import android.app.Application;

import com.example.kingwen.loadpictures.Beans.Contacts;
import com.example.kingwen.loadpictures.Utils.ContactsDataBase;

import java.util.List;

/**
 * Created by kingwen on 2016/3/24.
 */
public class MyApplication extends Application {

    /**
     * dialog中回调函数作为参数进行回调，然后在mainActivity中进行事件的监听
     */
    public  static  final int ID_RESET=1111;
    public  static  final int ID_DELETE=2222;

    /**
     * addContactsDialog中作为参数进行回调
     */
    public  static  final int ID_cancel=3333;
    public  static  final int ID_confirm=4444;

    /**
     *   数据库对象,在dialog弹出框收藏操作进行插入。在收藏界面作为数据源进行匹配
     */
    private ContactsDataBase contactsDataBase;


    //数据源链表，从数据库中读出，然后用于之后的适配器的操作
    private List<Contacts> mdatas;


    public List<Contacts> getMdatas() {
        return mdatas;
    }

    public void setMdatas(List<Contacts> mdatas) {
        this.mdatas = mdatas;
    }


    public ContactsDataBase getContactsDataBase() {
        return contactsDataBase;
    }

    public void setContactsDataBase(ContactsDataBase contactsDataBase) {
        this.contactsDataBase = contactsDataBase;
    }

}
