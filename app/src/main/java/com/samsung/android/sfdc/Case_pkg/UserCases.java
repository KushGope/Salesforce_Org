package com.samsung.android.sfdc.Case_pkg;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.sfdc.Api_pkg.Auth;
import com.samsung.android.sfdc.R;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCases extends AppCompatActivity {
    // Define user names and details
    private static final String[] USER_NAMES = {"abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc",};
    private static final String[] USER_ID = {
            "Premier Support",
            "0052y000003whXRAAY",
            "0050K00000AeGHOQA3",
            "0050K00000Ae78DQAR",
            "0052y000003w8XWAAY",
            "0052y000003wEgtAAE",
            "0052y000004rfMpAAI",
            "0052y000003wSFhAAM"
    };

    private Spinner userNamesSpinner;
    private TextView userDetailsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_case_layout);

        // Initialize UI components
        userNamesSpinner = findViewById(R.id.user_names);
        userDetailsTextView = findViewById(R.id.user_details);

        // Create an adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, USER_NAMES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userNamesSpinner.setAdapter(adapter);

        // Set listener for item selection
        userNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // userDetailsTextView.setText(USER_DETAILS[position]);
                if (USER_NAMES[position].equals("ETS"))
                    getETSCase(USER_ID[position]);
                else
                    getCasebyUser(USER_ID[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userDetailsTextView.setText("Details will appear here");
            }
        });

    }

    void getCasebyUser(String user) {

        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = "SELECT CaseNumber,Subject,Category__c,IsClosed FROM Case where Status<>'Resolved' and IsClosed=false and OwnerL3__c='" + user + "'";

        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (!response.isSuccessful()) {
                    // Handle error
                    System.out.println(response.errorBody());
                    userDetailsTextView.setText(response.errorBody().toString());
                }
                CaseResponse post = response.body();
                StringBuilder output = new StringBuilder();
                System.out.println(output);
                for (Case mCase : post.getRecords()) {
                    if (!mCase.getClosed()) {
                        output.append("\n" + mCase.getCaseNumber() + " " + mCase.getCategory__c() + "\n" + mCase.getSubject()).append("\n");
                    }
                }
                output.insert(0, user + "\n\n");
                output.insert(0, post.getTotalSize() + "\n");

                userDetailsTextView.setText(output);

            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                userDetailsTextView.setText(t.getMessage());
            }
        });

    }

    void getETSCase(String caseType) {

        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = "SELECT CaseNumber,Subject,Category__c,IsClosed FROM Case where  Status<>'Resolved' and IsClosed=false AND CaseType__c='"
                + caseType
                + "' AND (OwnerL3__c = '" + USER_ID[1]
                + "' OR OwnerL3__c = '" + USER_ID[2]
                + "' OR OwnerL3__c = '" + USER_ID[3]
                + "' OR OwnerL3__c = '" + USER_ID[4]
                + "' OR OwnerL3__c = '" + USER_ID[5]
                + "' OR OwnerL3__c = '" + USER_ID[6]
                + "' OR OwnerL3__c = '" + USER_ID[7]
                + "')";

        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (response.isSuccessful()) {
                    CaseResponse post = response.body();
                    StringBuilder output = new StringBuilder();
                    System.out.println(output);
                    for (Case mCase : post.getRecords()) {
                        if (!mCase.getClosed()) {
                            output.append(mCase.getCaseNumber() + " " + mCase.getSubject() + "\n").append("\n");
                        }
                    }
                    output.insert(0, caseType + "\n\n");
                    output.insert(0, post.getTotalSize() + "\n");

                    userDetailsTextView.setText(output);
//                    text.setText(output);
                } else {
                    // Handle error
                    System.out.println(response.errorBody());
//                    text.setText(response.errorBody().toString());
                    userDetailsTextView.setText(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                userDetailsTextView.setText(t.getMessage());
            }
        });

    }

}
