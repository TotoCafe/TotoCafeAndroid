package com.sohos.totocafeandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by dilkom71 on 24.11.2015.
 */
//this fragment displays the button*/
public class ButtonFragment extends Fragment {

    public ButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_button_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        handle the button click
        Button button = (Button) view.findViewById(R.id.buttonShowContacts);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                call the MainActivity's showContactsList() method when button pressed
                Deneme.showContactsList();
            }
        });
    }
}
