package com.unitech.photosapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final String JSON_URL="https://jsonplaceholder.typicode.com/photos";
    DialogClass dialogClass;
    DBHelper myDB;
    int lastVisible=0;
    private boolean isScrolling = false;
    private boolean isLastItemReached = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        dialogClass=new DialogClass(this);
        myDB=new DBHelper(this);

        int rowsInDB=myDB.getData(0,5).getCount(); //to know list is already fetched or not
        //Toast.makeText(getApplicationContext(),String.valueOf(rowsInDB),Toast.LENGTH_SHORT).show();
        if (rowsInDB<1){
            dialogClass.startDialog();
            StringRequest stringRequest=new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //show progress invisible

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            myDB.insertData(jsonObject.getInt("albumId"),jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getString("url"),jsonObject.getString("thumbnailUrl"),"N");
                        }

                        dialogClass.dismissDialog();
                        funToUpdateRecycler();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialogClass.dismissDialog();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialogClass.dismissDialog();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            funToUpdateRecycler();
        }

    }

    private void funToUpdateRecycler() {
        List<MyListPhoto> mDataList=new ArrayList<>();
        MyAdapter myAdapter=new MyAdapter(this,mDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        Cursor rs=myDB.getData(0,10);
        rs.moveToFirst();

        for (int j=0;j<rs.getCount();j++){
            MyListPhoto myListPhoto=new MyListPhoto();
            int indexImgId=rs.getColumnIndex(DBHelper.COL_PHOTO_ID);
            myListPhoto.setImgId(rs.getString(indexImgId));

            int indexTitle=rs.getColumnIndex(DBHelper.COL_TITLE);
            myListPhoto.setmTitle(rs.getString(indexTitle));

            int indexUrl=rs.getColumnIndex(DBHelper.COL_URL);
            myListPhoto.setmUrl(rs.getString(indexUrl));

            int indexThumb=rs.getColumnIndex(DBHelper.COL_THUMBNAIL_URL);
            myListPhoto.setThumbUrl(rs.getString(indexThumb));

            int indexFav=rs.getColumnIndex(DBHelper.COL_FAVORITE);
            myListPhoto.setFav(rs.getString(indexFav));

            mDataList.add(myListPhoto);
            rs.moveToNext();
        }
        rs.close();

        myAdapter.notifyDataSetChanged();
        lastVisible=rs.getCount();

        RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager=((LinearLayoutManager) recyclerView.getLayoutManager());
                int firstVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition();
                int visibleItemCount=linearLayoutManager.getChildCount();
                int totalItemCount=linearLayoutManager.getItemCount();

                if (isScrolling && (firstVisibleItemPosition+visibleItemCount==totalItemCount) && !isLastItemReached){
                    isScrolling=false;

                    Cursor rs=myDB.getData(lastVisible,10); //for getting 10 more rows from db
                    rs.moveToFirst();

                    for (int j=0;j<rs.getCount();j++){
                        MyListPhoto myListPhoto=new MyListPhoto();
                        int indexImgId=rs.getColumnIndex(DBHelper.COL_PHOTO_ID);
                        myListPhoto.setImgId(rs.getString(indexImgId));

                        int indexTitle=rs.getColumnIndex(DBHelper.COL_TITLE);
                        myListPhoto.setmTitle(rs.getString(indexTitle));

                        int indexUrl=rs.getColumnIndex(DBHelper.COL_URL);
                        myListPhoto.setmUrl(rs.getString(indexUrl));

                        int indexThumb=rs.getColumnIndex(DBHelper.COL_THUMBNAIL_URL);
                        myListPhoto.setThumbUrl(rs.getString(indexThumb));

                        int indexFav=rs.getColumnIndex(DBHelper.COL_FAVORITE);
                        myListPhoto.setFav(rs.getString(indexFav));

                        mDataList.add(myListPhoto);
                        rs.moveToNext();
                    }
                    rs.close();

                    myAdapter.notifyDataSetChanged();
                    lastVisible=lastVisible+10;

                    if (lastVisible>5000){
                        isLastItemReached=true;
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);

    }
}