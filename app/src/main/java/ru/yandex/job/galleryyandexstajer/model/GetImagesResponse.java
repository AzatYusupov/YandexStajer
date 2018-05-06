package ru.yandex.job.galleryyandexstajer.model;

import java.util.List;

public class GetImagesResponse {

    private int totalHits;
    private List<Image> hits;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Image> getHits() {
        return hits;
    }

    public void setHits(List<Image> hits) {
        this.hits = hits;
    }
}
