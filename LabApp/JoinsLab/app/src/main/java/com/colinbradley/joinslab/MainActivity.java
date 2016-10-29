package com.colinbradley.joinslab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.colinbradley.joinslab.recyclerviews.EmployeeAdapter;
import com.colinbradley.joinslab.recyclerviews.EmployeeHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView mRV;

    Button mEmployeesFromMacys, mCompaniesFromBoston, mHighestSalary;

    EmployeeAdapter mAdapter;

    Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = Helper.getInstance(this);
        mHelper.addEmployeesToDB();

        mRV = (RecyclerView)findViewById(R.id.recyclerView);
        mRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new EmployeeAdapter(new ArrayList<String>());
        mRV.setAdapter(mAdapter);



        mEmployeesFromMacys = (Button)findViewById(R.id.sameCompany);
        mCompaniesFromBoston = (Button)findViewById(R.id.companiesBoston);
        mHighestSalary = (Button)findViewById(R.id.highestSalary);

        mEmployeesFromMacys.setOnClickListener(this);
        mCompaniesFromBoston.setOnClickListener(this);
        mHighestSalary.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        List<String> list = new LinkedList<>();
        switch (view.getId()){
            case R.id.sameCompany:
                list = mHelper.getEmployeesFromMacys();
                mAdapter.updateList(list);
                break;
            case R.id.companiesBoston:
                list = mHelper.getCompaniesFromBoston();
                mAdapter.updateList(list);
                break;
            case R.id.highestSalary:
                list = mHelper.getHighestSalary();
                mAdapter.updateList(list);
                break;
        }
    }
}
