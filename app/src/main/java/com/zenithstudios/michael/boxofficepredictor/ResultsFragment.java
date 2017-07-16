package com.zenithstudios.michael.boxofficepredictor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment{
    TextView result;
    String finalresult;


    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        finalresult = this.getArguments().getString("Prediction");
        result = (TextView)view.findViewById(R.id.results);
        result.setText(finalresult+" million dollars");
        return view;
    }

}
