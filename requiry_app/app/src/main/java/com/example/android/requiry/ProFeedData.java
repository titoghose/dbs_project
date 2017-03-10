package com.example.android.requiry;

/**
 * Created by Aayush on 06-Mar-17.
 */
public class ProFeedData {
    private int pID;
    private String pname;
    private String created_by;
    private String domain;
    private String project_desc;
    private String start_date;
    private String end_date;
    public ProFeedData(int id,String prjname,String creator,String dom,String stdate,String edate,String prjdesc){
        pID = id;
        pname = prjname;
        created_by = creator;
        project_desc = prjdesc;
        start_date = stdate;
        end_date = edate;
        domain = dom;
    }
    public int getpID(){ return pID; }
    public String getPname(){
        return pname;
    }
    public String getCreator(){
        return created_by;
    }
    public String getDomain(){
        return domain;
    }
    public String getStart_date(){
        return start_date;
    }
    public String getEnd_date(){
        return end_date;
    }
    public String getProject_desc(){
        return project_desc;
    }
}
