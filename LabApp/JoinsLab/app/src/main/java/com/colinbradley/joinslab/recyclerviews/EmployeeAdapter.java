package com.colinbradley.joinslab.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by colinbradley on 10/28/16.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

    private List<String> mEmployeeList;

    public EmployeeAdapter(List<String> employeeList){
        mEmployeeList = employeeList;
    }
    @Override
    public EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new EmployeeHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(EmployeeHolder holder, int position) {
        holder.mTextView.setText(mEmployeeList.get(position));

    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    public void updateList (List<String> list){
        mEmployeeList = list;
        notifyDataSetChanged();
    }
}
