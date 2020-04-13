package com.example.shopmovies;

import android.os.AsyncTask;

import org.json.JSONObject;

public class GetUserInfo extends AsyncTask<String, Void, UserInfo> {
    private OnTaskCompleted onTaskCompleted;

    public GetUserInfo(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected UserInfo doInBackground(String... token) {
        try {
            String data = HttpHandler.makeServiceCall("https://graph.facebook.com/v5.0/me?access_token=" + token[0] + "&fields=id%2Cname%2Cemail%2Cbirthday%2Cpicture.width(200)&format=json");
            JSONObject jsonObject = new JSONObject(data);
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String image = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
            String birthday = jsonObject.getString("birthday");
            return new UserInfo(name, email, image, birthday);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
        super.onPostExecute(userInfo);
        onTaskCompleted.onGetUserCompleted(userInfo);
    }

    public interface OnTaskCompleted {
        void onGetUserCompleted(UserInfo userInfo);
    }
}
