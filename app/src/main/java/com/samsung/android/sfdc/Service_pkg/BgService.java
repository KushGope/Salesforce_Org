package com.samsung.android.sfdc.Service_pkg;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.samsung.android.sfdc.Api_pkg.Auth;
import com.samsung.android.sfdc.Case_pkg.Case;
import com.samsung.android.sfdc.Case_pkg.CaseResponse;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BgService extends Service {
    private final IBinder binder = new LocalBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        output = new StringBuilder("empty Queue!");
        Thread refresh = new Thread(new Runnable() {
            @Override
            public void run() {
                //refreshing data in background
                while (true) {
                    getCaseList();//refresh the queue
                    Log.e("Sfdc", "Case Queue refreshed !");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Log.e("Sfdc", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        refresh.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public class LocalBinder extends Binder {
        public BgService getService() {
            output = new StringBuilder("empty Queue!");
            return BgService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private StringBuilder output;
    public StringBuilder getOutput() {
        return output;
    }

    private String getCaseList() {
        String AuthToken = "Bearer " + Auth.getAuthToken();
        // StringBuilder output = new StringBuilder();
        String query = "SELECT CaseNumber,Subject,Category__c,IsClosed FROM Case where IsClosed=false and Owner.Name='bacchan'";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (response.isSuccessful()) {
                    CaseResponse post = response.body();
                    int count = 0;
                    output.setLength(0);
                    for (Case mCase : post.getRecords()) {
                        if (!mCase.getClosed()) {
                            ++count;
                            output.append(mCase.getCaseNumber()).append("\n");
                        }
                    }
                    output.insert(0, count + "\n");

                } else {
                    // Handle error
                    System.out.println(response.errorBody());
                    output = new StringBuilder(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                output = new StringBuilder(t.getMessage());
            }
        });
        return output.toString();
    }

}
