package com.zenithstudios.michael.boxofficepredictor;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    TextView emailLink;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about, container, false);
        emailLink = (TextView)v.findViewById(R.id.emailLink);
        emailLink.setPaintFlags(emailLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        emailLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"michael.he2001@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, "");

                startActivity(Intent.createChooser(sendEmail, "Send mail..."));




            }
        });


        return v;
    }

}
