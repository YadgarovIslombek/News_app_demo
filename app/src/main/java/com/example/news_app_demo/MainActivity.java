package com.example.news_app_demo;

import static com.example.news_app_demo.util.Constant.API_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_app_demo.Adapter.NewsAdapter;
import com.example.news_app_demo.Api.RetrofitClient;
import com.example.news_app_demo.model.NewsModel;
import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.util.Constant;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView,rv2;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ObjectDataClass> objData = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private TextView topHeadline;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;
    SharedPreferences.Editor editor;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");


        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

    }
    public void LoadJson(final String keyword){

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);



        String country = Util.getCountry();
        String language = Util.getLanguage();

        Call<NewsModel> call;

        if (keyword.length() > 0 ){
            call = RetrofitClient.getData(MainActivity.this).getNewsSearch(keyword, language, "publishedAt", API_TOKEN);
        } else {
            call = RetrofitClient.getData(MainActivity.this).getData(country, API_TOKEN);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){

                    if (!objData.isEmpty()){
                        objData.clear();
                    }

                    objData = response.body().getArticles();
                    newsAdapter = new NewsAdapter(objData, MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                    initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.no,
                        "Oops..",
                        "?????????????????????? ????????????????! ????????????????????, ???????????????????? ?????? ??????\n"+
                                t.toString());
            }
        });

        }

    private void initListener(){

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, NewsInfoActivity.class);

                ObjectDataClass objData1 = objData.get(position);
                intent.putExtra("url", objData1.getUrl());
                intent.putExtra("title", objData1.getTitle());
                intent.putExtra("img",  objData1.getUrlToImage());
                intent.putExtra("date",  objData1.getPublishedAt());
                intent.putExtra("source",  objData1.getSource().getName());
                intent.putExtra("author",  objData1.getAuthor());

                /*Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this, pair
                );
*/

             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    startActivity(intent, optionsCompat.toBundle());
                }else {*/
                    startActivity(intent);
               // }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("?????????? ?????????????????? ??????????????...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    onLoadingSwipeRefresh(query);
                }
                else {
                    Toast.makeText(MainActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings){
           Intent i =  new Intent(MainActivity.this, SettingActivity.class);
           startActivity(i);
           finish();
            return true;
        }else if(id == R.id.action_logOut){
            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
            dialogBuilder
                    .withTitle("??????????")
                    .withMessage("?????????? ???? ?????????????????????")
                    .withEffect(Slidetop)
                    .withDialogColor("#FF6200EE") //use color code for dialog
                    .withButton1Text("????")
                    .withButton2Text("??????")
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editor.putString(Constant.SP_PHONE, "");
                            editor.putString(Constant.SP_PASSWORD, "");
                            editor.putString(Constant.SP_USER_NAME, "");
                            editor.putString(Constant.SP_USER_TYPE, "");
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), LoginRegister.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            dialogBuilder.dismiss();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogBuilder.dismiss();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh() {
        LoadJson("");
    }
    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }
    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });

    }


}