package com.samsung.android.sfdc.Comment_pkg;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.sfdc.Api_pkg.Auth;
import com.samsung.android.sfdc.R;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comments extends AppCompatActivity {

    private EditText numberInputEditText;
    private TextView commentsTextView;
    private Button fetchCommentsButton;
    private Button addCommentsButton;
    String AuthToken = "";
    String CaseId;
    String json;
    HashMap<String, ArrayList<String>> Allcomments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_layout);
        setTitle("Comments API");

        Allcomments = new HashMap<>();
        numberInputEditText = findViewById(R.id.numberInputEditText);
        commentsTextView = findViewById(R.id.commentsTextView);
        fetchCommentsButton = findViewById(R.id.fetchCommentsButton);
        addCommentsButton = findViewById(R.id.addCommentsButton);

        fetchCommentsButton.setOnClickListener(v -> {
            String numberStr = numberInputEditText.getText().toString().trim();
            // fetchComments(numberStr);
            json = readJson("data.json");
            extractIdsFromJson(json);
        });

        addCommentsButton.setOnClickListener(v -> {

            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0, j = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("Id");
                    ArrayList<String> mComments = Allcomments.get(id);
                    if (mComments != null)
                        jsonObject.put("CommentBody__c", mComments);
                }
                json = jsonArray.toString();
                saveJsonToFile(json);
            } catch (Exception e) {
                Log.e("Json", "Error parsing JSON: " + e.getMessage());
            }

        });

    }

    void fetchComments(String numberStr) {
        commentsTextView.setText("Fetching comments...\n");
        ArrayList<String> tempcomments = new ArrayList<>();

        AuthToken = "Bearer " + Auth.getAuthToken();
//        String caseId = getCaseId(numberStr);
        String query = "SELECT CommentBody__c,FeedItemId__c,CreatedDate from CaseComment__c where Case__c ='" + numberStr + "' AND FeedItemId__c LIKE '%00%' ORDER BY CreatedDate DESC LIMIT 10";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CommentResponse> call = apiInterface.getCommentList(AuthToken, query);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {

                    CommentResponse post = response.body();
                    int count = 0;
                    StringBuilder output = new StringBuilder();
                    System.out.println(output);
                    for (CaseComment__c mComment : post.getRecords()) {
                        ++count;
                        tempcomments.add(mComment.getCommentBody__c());
                    }
                    Log.e("Json", "Comment added for case: " + numberStr);
                    Allcomments.put(numberStr, tempcomments);
                } else {
                    System.out.println(response.errorBody());
                    Log.e("Comment:", " " + response.errorBody());
//                    commentsTextView.setText(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                commentsTextView.setText("Error fetching comments: " + t.getMessage());
            }
        });
    }

    String readJson(String fileName) {
        List<String> ids = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            Log.e("Json", "Error reading JSON file from assets: " + ex.getMessage());
            return null;
        }
    }

    public List<String> extractIdsFromJson(String json) {
        List<String> ids = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("Id");
                fetchComments(id);
                ids.add(id);
            }
        } catch (Exception e) {
            Log.e("Json", "Error parsing JSON: " + e.getMessage());
            return null;
        }
        return ids;
    }

    private void saveJsonToFile(String json) {
        try (FileOutputStream fos = openFileOutput("newdata.json", MODE_PRIVATE)) {
            fos.write(json.getBytes());
            Log.e("Json", "new json added to: " + "/data/data/com.samsung.android.sfdc/files/newdata.json");
            // /data/data/com.samsung.android.sfdc/files/newdata.json
        } catch (IOException e) {
            Log.e("Json", "Error writing JSON to file: " + e.getMessage());
        }
    }
}
