package com.example.kingwen.loadpictures.widght;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kingwen.loadpictures.Beans.Contacts;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;

/**
 * Created by kingwen on 2016/3/30.
 */
public class AddContactsDialog extends Dialog {
    //图片展示
    private ImageView iv_show;

    //名字输入框
    private EditText et_name;

    //号码输入框
    private EditText et_number;

    //取消按钮
    private Button btn_cancel;

    //确认按钮
    private Button btn_confirm;

    //回调监听器
    private AddContactsDialogListener mlistener;

    //照片地址
    private String photoUrl;

    //名字
    private String name;

    //号码
    private String number;

    //上下文对象
    private Context mContext;

    //dialog的名字
    private String dialogName;


    //定义回调事件
    public interface  AddContactsDialogListener{
        void back( int id,Contacts mContact);
    }



    public AddContactsDialog(Context context,String diaName,AddContactsDialogListener mylistener) {
        super(context);
        mContext=context;
        mlistener=mylistener;
        dialogName=diaName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addcontacts_widght);

        initViews();

        initListeners();


    }

    private void initListeners() {
        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 实现本地选择图片的操作（后期改为更新到本程序数据库的操作）然后实现glide库的图片加载
                *
                * */
                photoUrl="";

                Toast.makeText(getContext(),"选择图片",Toast.LENGTH_SHORT).show();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("addContactsDialog","btn_canel_onclick");
                mlistener.back(MyApplication.ID_cancel,null);
                dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("addContactsDialog","btn_confirm_onclick");
                name=et_name.getText().toString();
                number=et_number.getText().toString();
                if(!("".equals(name))&&(!"".equals(number))){

                    Contacts mContacts=new Contacts(name,number,photoUrl);
                    mlistener.back(MyApplication.ID_confirm,mContacts);
                    //dialog关闭
                    dismiss();

                }else{
                    Toast.makeText(getContext(),"name和number都不能为空",Toast.LENGTH_SHORT).show();
                    et_number.setText("");
                    et_name.setText("");
                }
            }
        });
    }

    private void initViews() {
        //对话框名字
        setTitle(dialogName);

        iv_show= (ImageView) findViewById(R.id.iv_showImg_addContactsDialog);
        et_name= (EditText) findViewById(R.id.tv_contactsName_addContactsDialog);
        et_number= (EditText) findViewById(R.id.tv_contactsNumber_addContactsDialog);
        btn_cancel= (Button) findViewById(R.id.btn_cancel_addContactsDialog);
        btn_confirm= (Button) findViewById(R.id.btn_confirm_addContactsDialog);
    }
}
