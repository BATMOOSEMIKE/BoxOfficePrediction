package com.zenithstudios.michael.boxofficepredictor;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {




    public MainFragment() {
        // Required empty public constructor

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Check if it's a 4.4 phone, and give them some helpful tips
        if(Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT ==20){
            new AlertDialog.Builder(this.getActivity()).setTitle("Android Kitkat or below").setMessage("It seems like you're using an older version of android. Swipe from the left to see the navigation drawer!").setNeutralButton("OK", null).show();
        }

        return inflater.inflate(R.layout.fragment_main, container, false);

    }




}
