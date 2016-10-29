package com.colinbradley.joinslab.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by colinbradley on 10/28/16.
 */

public class EmployeeHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;

    public EmployeeHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(android.R.id.text1);
    }
}
