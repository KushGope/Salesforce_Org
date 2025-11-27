package com.samsung.android.sfdc.Report_pkg;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.sfdc.R;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reports extends AppCompatActivity {
    CaseCount mCaseCount;
    ResultFragment resultFragment;
    Bundle bundle;
    private final String text = "Reports API";
    private EditText startDateInput;
    private EditText endDateInput;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);
        setTitle(text);
        Button caseGlobal = findViewById(R.id.CaseGSME);
        Button caseLocal = findViewById(R.id.CaseSRI);

        Button pickStartDate = findViewById(R.id.pickStartDate);
        Button pickEndDate = findViewById(R.id.pickEndDate);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);

        pickStartDate.setOnClickListener(v -> showDatePickerDialog(startDate, startDateInput));
        pickEndDate.setOnClickListener(v -> showDatePickerDialog(endDate, endDateInput));

        instance();
        caseGlobal.setOnClickListener(v -> preProcess("Global"));
        caseLocal.setOnClickListener(v -> preProcess("Local"));
    }

    private void instance() {
        bundle = new Bundle();
        mCaseCount = new CaseCount();
    }


    void preProcess(String flag) {
        setDateLimits();
        String startTime = getUTC(startDate);
        String endTime = getUTC(endDate);
        if (flag.equals("Global"))
            globalAPI(startTime, endTime);
        else if (flag.equals("Local"))
            localAPI(startTime, endTime);

    }

    void globalAPI(String startTime, String endTime) {
        GlobalAPI api = new GlobalAPI();
        api.countActiveCase(startTime, this, "start");
        api.countActiveCase(endTime, this, "end");
        api.countResolved(startTime, endTime, this);
        api.countClosed(startTime, endTime, this);
        api.countNew(startTime, endTime, this);
    }

    void localAPI(String startTime, String endTime) {
        LocalAPI api = new LocalAPI();
        api.countActiveCase(startTime, this, "start");
        api.countActiveCase(endTime, this, "end");
        api.countResolved(startTime, endTime, this);
        api.countClosed(startTime, endTime, this);
        api.countNew(startTime, endTime, this);
    }

    public void setStart(Integer caseCount) {
        mCaseCount.ActiveStart = caseCount;
        bundle.putInt("active Start", mCaseCount.ActiveStart);
        showInFragment();
    }

    public void setEnd(Integer caseCount) {
        mCaseCount.ActiveEnd = caseCount;
        bundle.putInt("active End", mCaseCount.ActiveEnd);
        showInFragment();
    }

    public void setResolved(Integer caseCount) {
        mCaseCount.Resolved = caseCount;
        bundle.putInt("resolved", mCaseCount.Resolved);
        showInFragment();
    }


    public void setClosed(Integer caseCount) {
        mCaseCount.Closed = caseCount;
        bundle.putInt("closed", mCaseCount.Closed);
        showInFragment();
    }

    public void setNew(Integer caseCount) {
        mCaseCount.NewTaken = caseCount;
        bundle.putInt("new", mCaseCount.NewTaken);
        showInFragment();
    }


    private void showInFragment() {
        resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);
        // Replace the current fragment with ResultFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.resultFragment, resultFragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.resultFragment, resultFragment).addToBackStack(null).commit();
    }

    private void showDatePickerDialog(Calendar calendar, EditText editText) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            editText.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setDateLimits() {
        // Set time fields to zeroth hour
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 1);
        startDate.set(Calendar.MILLISECOND, 0);

        // Set time fields to last hour
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);
        endDate.set(Calendar.MILLISECOND, 0);
    }

    private String getUTC(Calendar Date) {
        Date date = Date.getTime();
        ZonedDateTime newTime = date.toInstant().atZone(ZoneId.systemDefault());
        ZonedDateTime utcTime = newTime.withZoneSameInstant(ZoneId.of("UTC"));
        String UTC = utcTime.toString();
        return UTC.substring(0, UTC.length() - 5);
    }
}
