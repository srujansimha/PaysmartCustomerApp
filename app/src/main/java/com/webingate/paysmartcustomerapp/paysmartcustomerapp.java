package com.webingate.paysmartcustomerapp;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Panacea-Soft on 7/10/18.
 * Contact Email : teamps.is.cool@gmail.com
 */

public class paysmartcustomerapp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
    }
}
