package com.samsung.android.sfdc.Case_pkg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.sfdc.Api_pkg.Auth;
import com.samsung.android.sfdc.R;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;
import com.samsung.android.sfdc.Service_pkg.BgService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cases extends AppCompatActivity {

    TextView text;
    Button caseList, userCaseList, refreshList;
    String AuthToken = "";
    StringBuilder output;
    BgService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_layout);
        setTitle("Case API");

        caseList = findViewById(R.id.getCases);
        userCaseList = findViewById(R.id.getCasesbyUser);
        refreshList = findViewById(R.id.refreshQueue);
        text = findViewById(R.id.caseListHolder);
        text.setText("No Records");

        caseList.setOnClickListener(v -> getCaseList());
        userCaseList.setOnClickListener(gotoUserCases());
//        userCaseList.setOnClickListener(v -> getCasebyUser("kush"));//alias
        refreshList.setOnClickListener(v -> startRefresh());
    }

    private View.OnClickListener gotoUserCases() {
        return v -> {
            Intent intent = new Intent(Cases.this, UserCases.class);
            startActivity(intent);
        };
    }

    void startRefresh() {
        if (mBound) {
            text.setText("");
            Thread refresh = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Call a method from the LocalService..
                    output = mService.getOutput();
                    while (true) {
                        try {
                            updateRecords();
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Log.e("Sfdc", e.getMessage() + "");
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            refresh.start();
        }
    }



    void updateRecords() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("Sfdc", output.toString());
                if (output != null)
                    text.setText(output);
//                    text.append(output);
            }
        });
    }

    void getCaseList() {
        AuthToken = "Bearer " + Auth.getAuthToken();
        String query = "SELECT CaseNumber,Description,IsClosed FROM Case where IsClosed=false";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (response.isSuccessful()) {
                    CaseResponse post = response.body();
                    int count = 0;
                    StringBuilder output = new StringBuilder();
                    System.out.println(output);
                    for (Case mCase : post.getRecords()) {
//                        if (!mCase.getClosed()) {
                        ++count;
                        output.append(mCase.getCaseNumber()).append("\n");
//                        }
                    }
                    output.insert(0, count + "\n");
                    text.setText(output);
                } else {
                    // Handle error
                    System.out.println(response.errorBody());
                    text.setText(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService.
        Intent intent = new Intent(this, BgService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        Intent serviceIntent = new Intent(getApplicationContext(), BgService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    /**
     * Defines callbacks for service binding, passed to bindService().
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            BgService.LocalBinder binder = (BgService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };


}
