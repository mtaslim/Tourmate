package com.ityadi.app.tourmate.Response;

/**
 * Created by taslim on 8/24/2016.
 */
public class MomentResponse {
    private String msg;
    private String err;
    private String notify;
    private String budget;
    private String totalExpense;

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getNotify() {
        return notify;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }
}
