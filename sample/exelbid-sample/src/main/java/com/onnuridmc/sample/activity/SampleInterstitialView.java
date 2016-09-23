package com.onnuridmc.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.onnuridmc.exelbid.ExelBidInterstitial;
import com.onnuridmc.exelbid.common.ExelBidError;
import com.onnuridmc.exelbid.common.OnInterstitialAdListener;
import com.onnuridmc.sample.AppConstants;
import com.onnuridmc.sample.R;
import com.onnuridmc.sample.utils.PrefManager;

public class SampleInterstitialView extends Activity {

    private ExelBidInterstitial mInterstitialAd;

    private String mUnitId;

    private EditText mEdtAdUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_interstitialview);

        mEdtAdUnit = (EditText) findViewById(R.id.editText);

        mUnitId = PrefManager.getInterstialAd(this, PrefManager.KEY_INTERSTIAL_AD, "9a86243f08e9ca5a59bfa37f79fc16e120e65b6e");

        mEdtAdUnit.setText(mUnitId);

        mInterstitialAd = new ExelBidInterstitial(this, mUnitId);
        mInterstitialAd.setInterstitialAdListener(new OnInterstitialAdListener() {
            @Override
            public void onInterstitialLoaded() {
                findViewById(R.id.interstitial_show).setEnabled(true);
            }

            @Override
            public void onInterstitialShow() {

            }

            @Override
            public void onInterstitialDismiss() {

            }

            @Override
            public void onInterstitialClicked() {

            }

            @Override
            public void onInterstitialFailed(ExelBidError errorCode) {
                findViewById(R.id.interstitial_show).setEnabled(false);
            }
        });


        mInterstitialAd.setAge(30);
        mInterstitialAd.setGender(true);
        mInterstitialAd.addKeyword("level", "10");
        mInterstitialAd.setTestMode(AppConstants.TEST_MODE);

        findViewById(R.id.interstitial_show).setEnabled(false);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.interstitial_load) {

            String unitID = mEdtAdUnit.getText().toString();
            if(TextUtils.isEmpty(unitID)) {
                return;
            }
            mInterstitialAd.setAdUnitId(unitID);
            if(!unitID.equals(mUnitId)) {
                PrefManager.setPref(this, PrefManager.KEY_INTERSTIAL_AD, unitID);
            }

            mUnitId = unitID;

            if(mInterstitialAd != null) {
                mInterstitialAd.load();
            }
        } else if(v.getId() == R.id.interstitial_show) {
            if(mInterstitialAd != null) {
                if(mInterstitialAd.isReady())
                    mInterstitialAd.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(mInterstitialAd != null) {
            mInterstitialAd.destroy();
        }
        super.onDestroy();
    }
}