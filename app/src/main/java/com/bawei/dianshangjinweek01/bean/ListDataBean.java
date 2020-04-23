package com.bawei.dianshangjinweek01.bean;

import java.util.List;

public class ListDataBean {
    private String message;
    private String status;
    private List<ResultBean> result;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<ResultBean> getResult() {
        return result;
    }
    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
    public static class ResultBean {
        private String director;
        private String horizontalImage;
        private String imageUrl;
        private int movieId;
        private String name;
        private double score;
        private String starring;
        public String getDirector() {
            return director;
        }
        public void setDirector(String director) {
            this.director = director;
        }
        public String getHorizontalImage() {
            return horizontalImage;
        }
        public void setHorizontalImage(String horizontalImage) {
            this.horizontalImage = horizontalImage;
        }
        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public int getMovieId() {
            return movieId;
        }
        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public double getScore() {
            return score;
        }
        public void setScore(double score) {
            this.score = score;
        }
        public String getStarring() {
            return starring;
        }
        public void setStarring(String starring) {
            this.starring = starring;
        }
    }
}
