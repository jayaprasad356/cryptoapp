package com.greymatter.sprint.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StepHistoryResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("steps")
        @Expose
        private String steps;
        @SerializedName("calories")
        @Expose
        private String calories;
        @SerializedName("earn")
        @Expose
        private String earn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getEarn() {
            return earn;
        }

        public void setEarn(String earn) {
            this.earn = earn;
        }

    }
}
