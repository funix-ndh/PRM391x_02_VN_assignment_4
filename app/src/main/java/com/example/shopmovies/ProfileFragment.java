package com.example.shopmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private UserInfo userInfo;

    public ProfileFragment(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        new DownloadImageTask(view.findViewById(R.id.image)).execute(userInfo.getImage());
        ((TextView) view.findViewById(R.id.name)).setText(userInfo.getName());
        ((TextView) view.findViewById(R.id.email)).setText(userInfo.getEmail());
        ((TextView) view.findViewById(R.id.birthday)).setText(userInfo.getBirthday());
        return view;
    }
}
