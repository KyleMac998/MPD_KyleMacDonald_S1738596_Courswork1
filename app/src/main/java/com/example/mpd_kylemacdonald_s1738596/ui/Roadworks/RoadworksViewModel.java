package com.example.mpd_kylemacdonald_s1738596.ui.Roadworks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoadworksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoadworksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}