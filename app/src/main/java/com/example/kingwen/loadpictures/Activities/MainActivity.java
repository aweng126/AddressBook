package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kingwen.loadpictures.Adapters.RecycleviewAdapter;
import com.example.kingwen.loadpictures.Beans.Contacts;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;
import com.example.kingwen.loadpictures.Utils.ContactsDataBase;
import com.example.kingwen.loadpictures.widght.AddContactsDialog;
import com.example.kingwen.loadpictures.widght.MyDialog;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static  final String TAG=MainActivity.class.getSimpleName();


    //布局
    private RecyclerView mRecycleView;

    //适配器
    private  RecycleviewAdapter mAdapter;

    //布局管理器
    private  RecyclerView.LayoutManager mLayoutManager;

    /*
    * 用于存储数据的链表
    * */
    private ArrayList<Contacts> mydata;

    public static int choice;

    //数据库对象
    private ContactsDataBase mDataBase;

    //游标
    private Cursor mCuror;

    //全局变量的对象
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*
              * 自定义弹出dialog
              * */
                final AddContactsDialog mDialog = new AddContactsDialog(MainActivity.this, "对文章进行操作", new AddContactsDialog.AddContactsDialogListener() {
                    @Override
                    public void back(int id, Contacts mContact) {

                        switch (id){
                            case MyApplication.ID_cancel:
                                /*
                                * 如果之前是取消，就什么也不操作
                                * */
                                break;
                            case  MyApplication.ID_confirm:
                                mydata.add(mContact);
                                mDataBase.insert(mContact);
                                mAdapter.notifyItemInserted(mydata.size());

                                Log.e(TAG,"回调然后增加数据");

                                break;
                            default:{
                                /*
                                * 没有作用
                                * */
                            }
                        }



                       }
                   }

                );
                mDialog.show();

            }
        });

        initDatas();

        initViews();

        initListener();

    }

    /**
     * 针对于recycleview进行view的绑定和布局的监听
     */
    private void initViews() {

        mRecycleView= (RecyclerView) findViewById(R.id.recycleveiw_mainactivity);

        //设置固定大小
        mRecycleView.setHasFixedSize(true);

        mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(mLayoutManager);

        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mAdapter=new RecycleviewAdapter(this,mydata);

        mRecycleView.setAdapter(mAdapter);

    }

    /**
     * 进行数据的获取，作为适配器的数据源
     */
    private void initDatas() {
        /*
        * 数据库对象
        * */
        mDataBase=new ContactsDataBase(MainActivity.this);
        mCuror=mDataBase.select();

        /*
        *从数据库中将数据读出来
        * */

        mydata=new ArrayList<Contacts>();
        if (mCuror.moveToFirst()){
            do{

                String name=mCuror.getString(mCuror.getColumnIndex("name_contacts"));
                String number=mCuror.getString(mCuror.getColumnIndex("number_contacts"));
                String photoUrl=mCuror.getString(mCuror.getColumnIndex("photoUrl_contact"));

                Contacts mContact=new Contacts(name,number,photoUrl);
                mydata.add(mContact);

            }while(mCuror.moveToNext());

            Log.e(TAG,"数据库数据读取完毕"+mydata.size());
        }


        myApplication= (MyApplication) getApplication();
        myApplication.setContactsDataBase(mDataBase);
       myApplication.setMdatas(mydata);
      //  mydata= (ArrayList<Contacts>) myApplication.getMdatas();
        Log.e("MainActivity initview", mydata.size() + "");

    }

    /**
     * 为适配器添加监听事件。实现长按和单机两种事件的响应
     */
   private void initListener() {

      mAdapter.setOnItemClickListener(new RecycleviewAdapter.OnRecyclerViewItemClickListener() {

          @Override
          public void onItemClick(View view, int data) {

              Toast.makeText(MainActivity.this,"点击了"+data+mydata.get(data).getContactsName(),Toast.LENGTH_SHORT).show();


              /*
              * 这里的单击要实现打电话的功能
              * */


          }


          //长按按钮可弹出按钮，选择分享或者收藏
          public  void onItemLongClick(View view, final int data){

              Toast.makeText(MainActivity.this,"长按了view"+data,Toast.LENGTH_SHORT).show();

              choice=data;

              /*
              * 自定义弹出dialog
              * */
              final MyDialog myDialog=new MyDialog(MainActivity.this, "对文章进行操作", mydata.get(data), new MyDialog.OnMyDialogListener() {
                  @Override
                  public void back(int id,Contacts mContacts) {
                   switch (id){
                       case MyApplication.ID_RESET:{

                           /*
                           * 将当前的这个位置的数据进行更新
                           * */
                           Log.e(TAG, "myDialog回调机制reset");
                           mydata.set(data, mContacts);

                           /*
                           * 数据库的更新操作
                           * */
                           mDataBase.updata(data,mContacts);

                           break;
                       }
                       case MyApplication.ID_DELETE:{
                           /*
                           * 将当前view对应的联系人删除
                           * */
                           Log.e(TAG, "myDialog Delete");

                           mDataBase.delete(data);
                           mydata.remove(data);

                           mAdapter.notifyItemRemoved(data);

                           synchronized(mydata){
                               mydata.notify();
                           }


                           break;
                       }
                       default:{

                       }
                   }
                  }
              });

               myDialog.show();
          }
      });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_importContacts:

                /*
                * 用contentprovider实现导入功能
                * */
                break;
            case R.id.action_settings:

                /*
                * 设置功能
                * */

                break;
            default:{

            }
         }

        return super.onOptionsItemSelected(item);
    }
}
