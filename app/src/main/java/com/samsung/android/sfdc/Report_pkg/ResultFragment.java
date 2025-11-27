package com.samsung.android.sfdc.Report_pkg;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.samsung.android.sfdc.R;

public class ResultFragment extends Fragment {
    int activeStart, activeEnd, newC, resolvedC, closedC;
    TextView startView, endView, resolvedView, closedView, newView;
    Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        if (bundle == null) return null;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Find the TextView and set sample text
        TextView textView = view.findViewById(R.id.resultTextView);

        startView = view.findViewById(R.id.TVactiveS);
        endView = view.findViewById(R.id.TVactiveE);
        resolvedView = view.findViewById(R.id.TVresolved);
        closedView = view.findViewById(R.id.TVclosed);
        newView = view.findViewById(R.id.TVnew);

        bundle = getArguments();
        newC = bundle.getInt("new", 0);
        resolvedC = bundle.getInt("resolved", 0);
        closedC = bundle.getInt("closed", 0);
        activeStart = bundle.getInt("active Start", 0);
        activeEnd = bundle.getInt("active End", 0);
        updateUI(activeStart, activeEnd, newC, resolvedC, closedC);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    void updateUI(int activeStart, int activeEnd, int newC, int resolvedC, int closedC) {
        startView.setText(activeStart + "");
        endView.setText(activeEnd + "");
        resolvedView.setText(resolvedC + "");
        closedView.setText(closedC + "");
        newView.setText(newC + "");
        String result = "new: " + newC + " closed: " + closedC + " activeStart: " + activeStart + " activeEnd: " + activeEnd;
        Log.e("Report", result);
    }

}
