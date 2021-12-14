package com.unitech.photosapi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

public class SecondActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tvTitle;
    ImageButton btPrevious,btNext,btDelete;
    CheckBox cbFav;
    DBHelper myDB;
    int intId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        imageView=findViewById(R.id.imageViewBiggerView);
        tvTitle=findViewById(R.id.textViewSecondTitle);
        btPrevious=findViewById(R.id.buttonPrevious);
        btDelete=findViewById(R.id.imageButtonDelete);
        btNext=findViewById(R.id.buttonNext);
        cbFav=findViewById(R.id.checkBoxFav);

        myDB=new DBHelper(this);

        Bundle bundle=getIntent().getExtras();
        String imgId=bundle.getString("imgId");

        intId=Integer.parseInt(imgId);

        funToFetchImage();

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intId<5000){
                    funToFetchNextImage();
                }
            }
        });

        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intId>1){
                    funToFetchPreviousImage();
                }
            }
        });

        cbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check=cbFav.isChecked();
                if (check){
                    //true means Y ,here user want to uncheck
                    myDB.updateFav(intId,"Y");
                }else {
                    myDB.updateFav(intId,"N");
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteData(intId);
                Intent intent1=new Intent(SecondActivity.this,MainActivity.class);
                finish();
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void funToFetchImage() {
        // intId is the id of present image
        Cursor rs=myDB.getOneRecord(intId);
        rs.moveToFirst();

        int indexId=rs.getColumnIndex(DBHelper.COL_PHOTO_ID);
        intId=rs.getInt(indexId);

        int indexUrl=rs.getColumnIndex(DBHelper.COL_URL);
        String mUrl=rs.getString(indexUrl);

        int indexTitle=rs.getColumnIndex(DBHelper.COL_TITLE);
        String mTitle=rs.getString(indexTitle);

        int indexFav=rs.getColumnIndex(DBHelper.COL_FAVORITE);
        String mFav=rs.getString(indexFav);
        //Toast.makeText(getApplicationContext(),mFav,Toast.LENGTH_LONG).show();

        rs.close();

        GlideUrl url = new GlideUrl(mUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());
        Glide.with(this).load(url).into(imageView);
        tvTitle.setText(mTitle);
        if (mFav.equals("Y")){
            cbFav.setChecked(true);
        }else {
            cbFav.setChecked(false);
        }
    }


    private void funToFetchNextImage() {
        // intId is the id of present image
        Cursor rs=myDB.getOneRecordNext(intId);
        rs.moveToFirst();

        int indexId=rs.getColumnIndex(DBHelper.COL_PHOTO_ID);
        intId=rs.getInt(indexId);

        int indexUrl=rs.getColumnIndex(DBHelper.COL_URL);
        String mUrl=rs.getString(indexUrl);

        int indexTitle=rs.getColumnIndex(DBHelper.COL_TITLE);
        String mTitle=rs.getString(indexTitle);

        int indexFav=rs.getColumnIndex(DBHelper.COL_FAVORITE);
        String mFav=rs.getString(indexFav);
        //Toast.makeText(getApplicationContext(),mFav,Toast.LENGTH_LONG).show();

        rs.close();

        GlideUrl url = new GlideUrl(mUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());
        Glide.with(this).load(url).into(imageView);
        tvTitle.setText(mTitle);
        if (mFav.equals("Y")){
            cbFav.setChecked(true);
        }else {
            cbFav.setChecked(false);
        }
    }

    private void funToFetchPreviousImage() {
        // intId is the id of present image
        Cursor rs=myDB.getOneRecordPrevious(intId);
        rs.moveToFirst();

        int indexId=rs.getColumnIndex(DBHelper.COL_PHOTO_ID);
        intId=rs.getInt(indexId);

        int indexUrl=rs.getColumnIndex(DBHelper.COL_URL);
        String mUrl=rs.getString(indexUrl);

        int indexTitle=rs.getColumnIndex(DBHelper.COL_TITLE);
        String mTitle=rs.getString(indexTitle);

        int indexFav=rs.getColumnIndex(DBHelper.COL_FAVORITE);
        String mFav=rs.getString(indexFav);

        rs.close();

        GlideUrl url = new GlideUrl(mUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());
        Glide.with(this).load(url).into(imageView);
        tvTitle.setText(mTitle);
        if (mFav.equals("Y")){
            cbFav.setChecked(true);
        }else {
            cbFav.setChecked(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}