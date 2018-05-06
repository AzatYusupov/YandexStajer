package ru.yandex.job.galleryyandexstajer.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.yandex.job.galleryyandexstajer.model.GetImagesResponse;

public interface ApiInterface {

    @GET(".")
    Call<GetImagesResponse> getImages(@Query("key") String key, @Query("q") String type, @Query("image_type") String imageType);
}
