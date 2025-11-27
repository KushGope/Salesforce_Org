package com.samsung.android.sfdc.Report_pkg;

import com.samsung.android.sfdc.Api_pkg.Auth;
import com.samsung.android.sfdc.Case_pkg.CaseResponse;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalAPI {

    private static final String[] USER_ID = {"0052y000003whXRAAY", "0050K00000AeGHOQA3", "0050K00000Ae78DQAR", "0052y000003w8XWAAY", "0052y000003wEgtAAE", "0052y000004rfMpAAI", "0052y000003wSFhAAM"};
    private final String users = "where (OwnerL3__c='0052y000003whXRAAY' OR OwnerL3__c='0050K00000AeGHOQA3' OR OwnerL3__c='0050K00000Ae78DQAR' OR OwnerL3__c='0052y000003w8XWAAY' OR OwnerL3__c='0052y000003wEgtAAE' OR OwnerL3__c='0052y000004rfMpAAI' OR OwnerL3__c='0052y000003wSFhAAM') ";
    private final String Params = "SELECT CaseNumber,CreatedDate,ClosedDate,QueueL3MainManager__c,QueueGsmeAssignDateFromCaseKPI__c,IsClosed FROM Case ";
    private final String managers = " and (QueueL3MainManager__c ='0050K000007p4GNQAY' OR QueueL3MainManager__c ='0050K000007p4JtQAI')";


    void countActiveCase(String timestamp, final Reports mRepo, String flag) {
        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = Params + users + "and QueueGsmeAssignDateFromCaseKPI__c <= " + timestamp + " and (ClosedDate=null OR ClosedDate >= " + timestamp + ")" + managers;
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (!response.isSuccessful()) { // Handle error
                    System.out.println(response.errorBody());
                    return;
                }
                CaseResponse post = response.body();
                mRepo.setStart(post.getTotalSize());
                if (flag.equals("start"))
                    mRepo.setStart(post.getTotalSize());
                else if (flag.equals("end"))
                    mRepo.setEnd(post.getTotalSize());
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    void countResolved(String startTime, String endTime, final Reports mRepo) {
        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = Params + users + "and Resolved_Date__c >= " + startTime + " and Resolved_Date__c <= " + endTime + managers;
//        String query = "SELECT CaseNumber,IsClosed FROM Case where IsClosed=false";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (!response.isSuccessful()) { // Handle error
                    System.out.println(response.errorBody());
                    return;
                }
                CaseResponse post = response.body();
                mRepo.setResolved(post.getTotalSize());
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    void countClosed(String startTime, String endTime, final Reports mRepo) {
        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = Params + users + "and ClosedDate >= " + startTime + " and ClosedDate <= " + endTime + managers;
//        String query = "SELECT CaseNumber,IsClosed FROM Case where IsClosed=false";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (!response.isSuccessful()) { // Handle error
                    System.out.println(response.errorBody());
                    return;
                }
                CaseResponse post = response.body();
                mRepo.setClosed(post.getTotalSize());
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    void countNew(String startTime, String endTime, final Reports mRepo) {
        String AuthToken = "Bearer " + Auth.getAuthToken();
        String query = Params + users + "and QueueGsmeAssignDateFromCaseKPI__c >= " + startTime + " and QueueGsmeAssignDateFromCaseKPI__c <= " + endTime + managers;
//        String query = "SELECT CaseNumber,IsClosed FROM Case where IsClosed=false";
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi();
        Call<CaseResponse> call = apiInterface.getCaseList(AuthToken, query);
        call.enqueue(new Callback<CaseResponse>() {
            @Override
            public void onResponse(Call<CaseResponse> call, Response<CaseResponse> response) {
                if (!response.isSuccessful()) { // Handle error
                    System.out.println(response.errorBody());
                    return;
                }
                CaseResponse post = response.body();
                mRepo.setNew(post.getTotalSize());
            }

            @Override
            public void onFailure(Call<CaseResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
