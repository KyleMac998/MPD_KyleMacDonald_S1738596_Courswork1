// Kyle MacDonald S1738596


package com.example.mpd_kylemacdonald_s1738596.ui.Incident;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncidentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IncidentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}