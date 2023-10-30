package com.webingate.paysmartcustomerapp.fragment.customerAppFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.webingate.paysmartcustomerapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class customerappBottomDialogFragment extends BottomSheetDialogFragment {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    String[] fruits;

    public customerappBottomDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fruits = getResources().getStringArray(R.array.fruits);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.customerapp_bottomdialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>(Arrays.asList(fruits));

        if (getContext() != null) {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            ListView listView = view.findViewById(R.id.bsDialogListView);

            FrameLayout frameLayout = view.findViewById(R.id.frameLayout);
            frameLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
