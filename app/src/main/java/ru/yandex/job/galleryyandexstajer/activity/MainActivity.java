package ru.yandex.job.galleryyandexstajer.activity;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.yandex.job.galleryyandexstajer.R;
import ru.yandex.job.galleryyandexstajer.adapter.RecyclerImageAdapter;
import ru.yandex.job.galleryyandexstajer.api.ApiClient;
import ru.yandex.job.galleryyandexstajer.api.ApiInterface;
import ru.yandex.job.galleryyandexstajer.model.GetImagesResponse;
import ru.yandex.job.galleryyandexstajer.model.Image;
import ru.yandex.job.galleryyandexstajer.utils.Settings;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshMain)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    private RecyclerImageAdapter adapter;
    private List<Image> images;
    private ApiInterface apiInterface;
    private SearchView searchView;
    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);

        initToolbar();
        initRecyclerView();
        loadImages();
        swipeRefreshLayout.setOnRefreshListener(()->loadImages());
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_action_hamburger);
        searchView = toolbar.findViewById(R.id.action_search);
        setupSearchView();
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        images = new ArrayList<>();
        adapter = new RecyclerImageAdapter(this, images);
        recyclerView.setAdapter(adapter);

        adapter.setOnImageItemClickListener(position -> startFullImageActivity(position));
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                loadImages();
                return false;
            }
        });
    }

    private void startFullImageActivity(int position) {
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        Intent intent = new Intent(this, FullImageActivity.class);
        intent.putExtra(Settings.FIELD_IMAGE_URLS, new Gson().toJson(images));
        intent.putExtra(Settings.FIELD_IMAGE_CUR, position);
        intent.putExtra(Settings.FIELD_IMAGE_SEARCH, searchText);
        startActivity(intent, bundle);
    }
    // Loading images from API
    private void loadImages() {
        Call<GetImagesResponse> getImagesResponseCall = apiInterface.getImages(Settings.apiKey, searchText, "photo");

        getImagesResponseCall.enqueue(new Callback<GetImagesResponse>() {
            @Override
            public void onResponse(Call<GetImagesResponse> call, Response<GetImagesResponse> response) {
                if (response.code()== HttpURLConnection.HTTP_OK)
                    showImages(response.body().getHits());
                else
                    showImages(images);
            }

            @Override
            public void onFailure(Call<GetImagesResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, R.string.error_imageload, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Showing images after loaded in recyclerview with adapter
    private void showImages(List<Image> loadImages) {
       images = loadImages;
       adapter.setImages(images);
       swipeRefreshLayout.setRefreshing(false);
       adapter.notifyDataSetChanged();
    }

}
