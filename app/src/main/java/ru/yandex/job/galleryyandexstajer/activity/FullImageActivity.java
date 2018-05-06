package ru.yandex.job.galleryyandexstajer.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.job.galleryyandexstajer.R;
import ru.yandex.job.galleryyandexstajer.adapter.SwipeImageAdapter;
import ru.yandex.job.galleryyandexstajer.model.Image;
import ru.yandex.job.galleryyandexstajer.utils.Settings;

public class FullImageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_activity_full_image)
    Toolbar toolbar;
//    @BindView(R.id.full_image)
//    ImageView fullImageView;
//    @BindView(R.id.progressFullImage)
//    ProgressBar progressBar;
    @BindView(R.id.view_paper)
    ViewPager viewPager;

    private List<Image> images;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        initToolbar();

        if (getIntent().hasExtra(Settings.FIELD_IMAGE_URLS)) {
            images = new Gson().fromJson(getIntent().getExtras().getString(Settings.FIELD_IMAGE_URLS),
                    new TypeToken<List<Image>>(){}.getType());
            int curPosition = getIntent().getExtras().getInt(Settings.FIELD_IMAGE_CUR);
            searchText = getIntent().getExtras().getString(Settings.FIELD_IMAGE_SEARCH);
            initSwipeAdapter(curPosition);

            setToolbarTitle(getIntent().getExtras().getInt(Settings.FIELD_IMAGE_CUR));
        }
    }

    private void initSwipeAdapter(int curPosition) {
        SwipeImageAdapter swipeAdapter = new SwipeImageAdapter(this, images);
        swipeAdapter.setOnPagerPageChangeListener(position -> setToolbarTitle(position));
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(curPosition);
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_action_backspace);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setToolbarTitle(int curPos) {
        toolbar.setTitle((curPos+1) + " из "+images.size() + ". "+searchText);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
