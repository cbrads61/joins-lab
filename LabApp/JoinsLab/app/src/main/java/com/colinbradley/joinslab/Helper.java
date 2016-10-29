package com.colinbradley.joinslab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colinbradley on 10/28/16.
 */

public class Helper extends SQLiteOpenHelper {
    public static final String DATABAE_NAME = "employee.db";
    public static final String EMPLOYEE_TABLE = "employee_table";
    public static final String COL_SSN = "ssn";
    public static final String COL_FIRST_NAME = "first";
    public static final String COL_LAST_NAME = "last";
    public static final String COL_YEAR_OF_BIRTH = "year_of_birth";
    public static final String COL_CITY = "city";

    public static final String JOBS_TABLE = "job_table";
    public static final String COL_J_SSN = "jssn";
    public static final String COL_COMPANY = "company";
    public static final String COL_SALARY = "salary";
    public static final String COL_EXP = "experience";

    private static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " +
            EMPLOYEE_TABLE + "(" +
            COL_SSN + " INTEGER PRIMARY KEY, " +
            COL_FIRST_NAME + " TEXT, " +
            COL_LAST_NAME + " TEXT, " +
            COL_YEAR_OF_BIRTH + " INTEGER, " +
            COL_CITY + " TEXT)";

    private static final String CREATE_JOB_TABLE = "CREATE TABLE " +
            JOBS_TABLE + "(" +
            COL_J_SSN + " INTEGER PRIMARY KEY, " +
            COL_COMPANY + " TEXT, " +
            COL_SALARY + " INTEGER, " +
            COL_EXP + " INTEGER)";


    private Helper(Context context){
        super(context, DATABAE_NAME, null, 1);
    }

    private static Helper INSTANCE;

    public static synchronized Helper getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new Helper(context.getApplicationContext());
            return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_JOB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JOBS_TABLE);
        onCreate(db);
    }

    public ContentValues insertEMPLOYEErow(int ssn, String firstName, String lastName, int yearOfBirth, String city){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SSN,ssn);
        values.put(COL_FIRST_NAME, firstName);
        values.put(COL_LAST_NAME, lastName);
        values.put(COL_YEAR_OF_BIRTH, yearOfBirth);
        values.put(COL_CITY, city);
        return values;
    }

    public ContentValues insertJOBrow(int j_ssn, String company, int salary, int exp){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_J_SSN, j_ssn);
        values.put(COL_COMPANY, company);
        values.put(COL_SALARY, salary);
        values.put(COL_EXP, exp);
        return values;
    }

    public void addEmployeesToDB(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues employee1 = insertEMPLOYEErow (123045678, "John", "Smith", 1973, "NY");
        ContentValues employee2 = insertEMPLOYEErow (123045679, "David", "McWill", 1982, "Seattle");
        ContentValues employee3 = insertEMPLOYEErow(123045680, "Katerina", "Wise", 1973, "Boston");
        ContentValues employee4 = insertEMPLOYEErow(123045681, "Donald", "Lee", 1992, "London");
        ContentValues employee5 = insertEMPLOYEErow(123045682, "Gary", "Henwood", 1987, "Las Vagas");
        ContentValues employee6 = insertEMPLOYEErow(123045683, "Anthony", "Bright", 1963, "Seattle");
        ContentValues employee7 = insertEMPLOYEErow(123045684, "William", "Newey", 1995, "Boston");
        ContentValues employee8 = insertEMPLOYEErow(123045685, "Melony", "Smith", 1970, "Chicago");

        db.insert(EMPLOYEE_TABLE, null, employee1);
        db.insert(EMPLOYEE_TABLE, null, employee2);
        db.insert(EMPLOYEE_TABLE, null, employee3);
        db.insert(EMPLOYEE_TABLE, null, employee4);
        db.insert(EMPLOYEE_TABLE, null, employee5);
        db.insert(EMPLOYEE_TABLE, null, employee6);
        db.insert(EMPLOYEE_TABLE, null, employee7);
        db.insert(EMPLOYEE_TABLE, null, employee8);

        ContentValues jobs1 = insertJOBrow(123045678, "Fuzz", 60, 1);
        ContentValues jobs2 = insertJOBrow(123045679, "GA", 70, 2);
        ContentValues jobs3 = insertJOBrow(123045680, "Little Place", 120, 5);
        ContentValues jobs4 = insertJOBrow(123045681, "Macy's", 78, 3);
        ContentValues jobs5 = insertJOBrow(123045682, "New Life", 65, 1);
        ContentValues jobs6 = insertJOBrow(123045683, "Believe", 158, 6);
        ContentValues jobs7 = insertJOBrow(123045684, "Macy's", 200, 8);
        ContentValues jobs8 = insertJOBrow(123045685, "Stop", 299, 12);

    }

    public List<String> getEmployeesFromMacys(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_FIRST_NAME + ", " + COL_LAST_NAME
                + " FROM " + EMPLOYEE_TABLE + " JOIN " + JOBS_TABLE
                + " ON " + EMPLOYEE_TABLE + "." + COL_SSN + " = "
                + JOBS_TABLE + "." + COL_J_SSN + " WHERE "
                + COL_COMPANY + " LIKE 'macy%'", null);

        List<String> macysEmployees = new ArrayList<>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                macysEmployees.add(cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                        + " " + cursor.getString(cursor.getColumnIndex(COL_LAST_NAME)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return macysEmployees;
    }

    public List<String> getCompaniesFromBoston(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_COMPANY + " FROM " +
                EMPLOYEE_TABLE + " JOIN " + JOBS_TABLE +
                " ON " + EMPLOYEE_TABLE + "." + COL_SSN +
                " = " + JOBS_TABLE + "." + COL_J_SSN +
                " WHERE " + COL_CITY + " LIKE 'boston'", null);
        List<String> coFromBoston = new ArrayList<>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                coFromBoston.add(cursor.getString(cursor.getColumnIndex(COL_COMPANY)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return coFromBoston;
    }

    public List<String> getHighestSalary(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_FIRST_NAME
                + ", " + COL_LAST_NAME + " FROM " + EMPLOYEE_TABLE
                + " JOIN " + JOBS_TABLE + " ON " + EMPLOYEE_TABLE + "." + COL_SSN
                + " = " + JOBS_TABLE + "." + COL_J_SSN
                + " ORDER BY " + COL_SALARY + " DESC LIMIT 1",null);

        List<String> highestSalary = new ArrayList<>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                highestSalary.add(cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME)) +
                        " " + cursor.getString(cursor.getColumnIndex(COL_LAST_NAME)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return highestSalary;
    }



}
