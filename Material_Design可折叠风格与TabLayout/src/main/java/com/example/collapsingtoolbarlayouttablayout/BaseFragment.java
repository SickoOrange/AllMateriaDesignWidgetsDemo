package com.example.collapsingtoolbarlayouttablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Orange on 2016/11/11.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return InitView(inflater,container);
    }

    public abstract View InitView(LayoutInflater inflater,@Nullable ViewGroup container);
}
