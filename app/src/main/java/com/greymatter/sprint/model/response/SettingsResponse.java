package com.greymatter.sprint.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingsResponse {

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
        @SerializedName("min_balance")
        @Expose
        private String min_balance;
        @SerializedName("max_users")
        @Expose
        private String max_users;
        @SerializedName("app_version")
        @Expose
        private String app_version;
        @SerializedName("update_type")
        @Expose
        private String update_type;
        @SerializedName("app_description")
        @Expose
        private String app_description;
        @SerializedName("app_link")
        @Expose
        private String app_link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMin_balance() {
            return min_balance;
        }

        public void setMin_balance(String min_balance) {
            this.min_balance = min_balance;
        }

        public String getMax_users() {
            return max_users;
        }

        public void setMax_users(String max_users) {
            this.max_users = max_users;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getUpdate_type() {
            return update_type;
        }

        public void setUpdate_type(String update_type) {
            this.update_type = update_type;
        }

        public String getApp_description() {
            return app_description;
        }

        public void setApp_description(String app_description) {
            this.app_description = app_description;
        }

        public String getApp_link() {
            return app_link;
        }

        public void setApp_link(String app_link) {
            this.app_link = app_link;
        }
    }
}
