package com.burra.modelclass;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private CountDownTimer countDownTimer;

    public void setCountDownTimer(CountDownTimer timer) {
        Log.e("2222222222222222222","start");
        this.countDownTimer = timer;
    }

    public void cancelCountDownTimer() {
            countDownTimer.cancel();
    }
}
