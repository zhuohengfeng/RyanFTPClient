package com.ryan.ftp.ftp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FtpService extends IntentService {

    public FtpService() {
        super("FtpService");
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, FtpService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("zhf1122", "FtpService onHandleIntent+++++");
        handleFtpFilesItemData();
    }

    private void handleFtpFilesItemData() {

    }


}
