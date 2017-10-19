package com.tianyuhou.paymo;

/**
 * Created by Yu on 10/13/2017.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




public class ButtonAddFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttong_fragment,container,false);
        return view;
    }
    private FloatingActionButton btn;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        btn = getActivity().findViewById(R.id.fab);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(),EditActivity.class);
                startActivity(intent);
            }
        });
    }
}
