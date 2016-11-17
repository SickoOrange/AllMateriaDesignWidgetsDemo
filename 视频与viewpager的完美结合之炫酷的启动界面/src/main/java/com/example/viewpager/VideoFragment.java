package com.example.viewpager;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Orange on 2016/11/17.
 */
public class VideoFragment extends Fragment {

    private MyVideoView videoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        videoView = new MyVideoView(getContext());
        int index = getArguments().getInt("index");
        Uri uri = null;
        switch (index) {
            case 0:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                        .raw.guide_1);
                break;
            case 1:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                        .raw.guide_2);
                break;
            case 2:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                        .raw.guide_3);
                break;
        }
        videoView.playVideo(uri);
        return videoView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
