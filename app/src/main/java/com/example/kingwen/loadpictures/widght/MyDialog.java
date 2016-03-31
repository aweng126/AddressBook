package com.example.kingwen.loadpictures.widght;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kingwen.loadpictures.Beans.Contacts;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;

/**
 * Created by kingwen on 2016/3/24.
 */
public class MyDialog  extends Dialog {

    private ImageView iv_show;

    private EditText et_contactName;

    private EditText et_contactNumber;

    private Button btn_reset;

    private  Button btn_delete;

    private Contacts item;

    private OnMyDialogListener mylistener;

    private String dialogName;

    private  Context mContext;

    //定义回调事件
    public interface  OnMyDialogListener{
        void back( int id,Contacts mContact);
    }

    /**
     * 自定义的dialog的构造函数
     * @param context 上下文对象
     * @param dialogName 弹出框的名字
     * @param mcontacts  联系人对象
     * @param mylistener   监听器
     */
    public MyDialog(Context context,String dialogName,Contacts mcontacts,OnMyDialogListener mylistener) {
        super(context);
        this.dialogName=dialogName;
        this.item=mcontacts;
        this.mylistener=mylistener;
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        initViews();

        initListeners();
    }

    /**
     * 为布局中的两个按钮添加监听事件
     */
    private void initListeners() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 更新
                * */
                String name=et_contactName.getText().toString();
                String number=et_contactNumber.getText().toString();
                String photourl="kingwen.com";

                if(!("".equals(name))&&(!"".equals(number))){

                    Contacts mContacts=new Contacts(name,number,photourl);
                    mylistener.back(MyApplication.ID_RESET,mContacts);
                    //dialog关闭
                    dismiss();

                }else{
                    Toast.makeText(getContext(),"不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 设置删除事件
                * */
                mylistener.back(MyApplication.ID_DELETE,null);
                //dialog关闭
                dismiss();
            }
        });
    }

    /**
     * 控件绑定
     */
    private void initViews() {
        //对话框名字
        setTitle(dialogName);

        iv_show= (ImageView) findViewById(R.id.iv_showImg_dialog);
        et_contactName= (EditText) findViewById(R.id.tv_contactsName_dialog);
        et_contactNumber= (EditText) findViewById(R.id.tv_contactsNumber_dialog);
        btn_reset= (Button) findViewById(R.id.btn_reset_dialog);
        btn_delete= (Button) findViewById(R.id.btn_delete_dialog);

       // Glide.with(mContext).load(item.getImgUrl()).into(iv_show);
        et_contactName.setText(item.getContactsName());
        et_contactNumber.setText(item.getContactNumber());

    }


}
