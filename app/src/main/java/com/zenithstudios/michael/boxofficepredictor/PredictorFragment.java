package com.zenithstudios.michael.boxofficepredictor;


import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PredictorFragment extends Fragment {
    Spinner movieGenre, releaseMonth;
    String myGenre, opening, finalnum;
    TextView urlDisplay;
    EditText openingInput;
    Button button;
    int myMonth;
    String[] monthsList = new String[]{"Month","January", "February", "March","April","May","June","July","August","September","October","November","December"};
    String[] genreList = new String[]{"Genre","Action", "Animated", "Comedy","Drama","Horror"};

    TotalSender totalSender;




    public PredictorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_predictor, container, false);


        //Make the screen not jiggle when I press away from keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        movieGenre = (Spinner)v.findViewById(R.id.genreSpinner);
        releaseMonth = (Spinner)v.findViewById(R.id.monthSpinner);
        urlDisplay = (TextView)v.findViewById(R.id.urlLink);
        urlDisplay.setPaintFlags(urlDisplay.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        openingInput=(EditText)v.findViewById(R.id.openingEdit);

        //sets keyboard focus listener for hiding it
        openingInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeKeyboard(getActivity(), openingInput.getWindowToken() );
                }
            }
        });


        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, monthsList);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, genreList);
        movieGenre.setAdapter(genreAdapter);
        releaseMonth.setAdapter(monthAdapter);

        releaseMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id)
            {
                int month = 0;
                if (position == 0) {
                    month = 0;
                } else if (position == 1) {
                    month = 1;
                } else if (position == 2) {
                    month = 2;
                } else if (position == 3) {
                    month = 3;
                } else if (position == 4) {
                    month = 4;
                } else if (position == 5) {
                    month = 5;
                } else if (position == 6) {
                    month = 6;
                } else if (position == 7) {
                    month = 7;
                } else if (position == 8) {
                    month = 8;
                } else if (position == 9) {
                    month = 9;
                } else if (position == 10) {
                    month = 10;
                } else if (position == 11) {
                    month = 11;
                }else if (position == 12){
                    month = 12;

                }

                myMonth = month;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        movieGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id)
            {
                String genre = "";
                if(position == 0){
                    genre="fake";
                }
                else if (position == 1) {
                    genre = "Action";
                } else if (position == 2) {
                    genre = "Animated";
                } else if (position == 3) {
                    genre = "Comedy";
                } else if (position == 4) {
                    genre = "Drama";
                } else if (position == 5) {
                    genre = "Horror";
                }
                myGenre = genre;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

        button = (Button)v.findViewById(R.id.predictButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opening = openingInput.getText().toString();
                //checks if editText is empty or not
                if(!TextUtils.isEmpty(openingInput.getText())){
                    String genresaved = myGenre;
                    int monthsaved = myMonth;
                    //checks if genre and month are legit
                    if(monthsaved != 0 && genresaved != "fake") {
                        double mulfactor = getMulFactor(genresaved, monthsaved);
                        double openingnum = Double.parseDouble(opening);
                        double totalnum = mulfactor * openingnum;
                        finalnum = String.format("%.2f", totalnum);

                        //this sends the total to the main activity using an interface
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                totalSender.sendTotal(finalnum);
                            }
                        }, 300);


                    } else{
                        Toast.makeText(getActivity(), "Please enter month and genre", Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    Toast.makeText(getActivity(), "Please enter opening weekend", Toast.LENGTH_SHORT).show();
                }


            }
        });
        movieGenre.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                closeKeyboard(getActivity(), openingInput.getWindowToken());
                return false;
            }
        }) ;
        releaseMonth.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                closeKeyboard(getActivity(), openingInput.getWindowToken());
                return false;
            }
        }) ;


        return v;

    }

    public interface TotalSender{
        void sendTotal(String total);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity currentActivity = (Activity)context;
        try{
            totalSender = (TotalSender)currentActivity;
        } catch(Exception e){}

    }

    // This method hides the keyboard
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    // This is the function/method for getting the multiplying factor :)
    private static double getMulFactor(String genresaved, int monthsaved) {

        if (genresaved.equalsIgnoreCase("Action")) {
            switch (monthsaved) {
                case 1:
                    return 3.247;
                case 2:
                    return 3.257;
                case 3:
                    return 2.746;
                case 4:
                    return 2.474;
                case 5:
                    return 2.455;
                case 6:
                    return 2.663;
                case 7:
                    return 3.210;
                case 8:
                    return 3.230;
                case 9:
                    return 3.571;
                case 10:
                    return 3.656;
                case 11:
                    return 2.787;
                case 12:
                    return 4.415;
            }
        } else if (genresaved.equalsIgnoreCase("Drama")) {
            switch (monthsaved) {
                case 1:
                    return 3.672;
                case 2:
                    return 3.02;
                case 3:
                    return 3.399;
                case 4:
                    return 3.373;
                case 5:
                    return 3.197;
                case 6:
                    return 3.470;
                case 7:
                    return 3.432;
                case 8:
                    return 3.834;
                case 9:
                    return 2.966;
                case 10:
                    return 4.144;
                case 11:
                    return 3.339;
                case 12:
                    return 4.890;
            }
        } else if (genresaved.equalsIgnoreCase("Horror")) {
            switch (monthsaved) {
                case 1:
                    return 2.022;
                case 2:
                    return 2.047;
                case 3:
                    return 2.217;
                case 4:
                    return 2.015;
                case 5:
                    return 2.105;
                case 6:
                    return 2.037;
                case 7:
                    return 2.551;
                case 8:
                    return 2.195;
                case 9:
                    return 2.229;
                case 10:
                    return 2.160;
                case 11:
                    return 2.186;
                case 12:
                    return 3.273;
            }
        } else if (genresaved.equalsIgnoreCase("Comedy")) {
            switch (monthsaved) {
                case 1:
                    return 3.123;
                case 2:
                    return 3.378;
                case 3:
                    return 3.824;
                case 4:
                    return 3.117;
                case 5:
                    return 3.264;
                case 6:
                    return 3.737;
                case 7:
                    return 3.238;
                case 8:
                    return 3.214;
                case 9:
                    return 3.63;
                case 10:
                    return 3.157;
                case 11:
                    return 3.286;
                case 12:
                    return 4.66;
            }
        } else if (genresaved.equalsIgnoreCase("Animated")) {
            switch (monthsaved) {
                case 1:
                    return 3.247;
                case 2:
                    return 3.68;
                case 3:
                    return 3.505;
                case 4:
                    return 3.534;
                case 5:
                    return 4.06;
                case 6:
                    return 3.501;
                case 7:
                    return 4.16;
                case 8:
                    return 4.184;
                case 9:
                    return 3.527;
                case 10:
                    return 3.737;
                case 11:
                    return 4.032;
                case 12:
                    return 5.133;
            }
        }
        return -1;
    }



}
