package com.makenv.model.mc.message.pojo;

import java.util.List;

/**
 * Created by wgy on 2017/2/23.
 */
public class Emis {

    private String calctype;

    private String actionlist;

    private List<String> model;

    private Cmaq cmaq;

    private Megan megan;

    public Megan getMegan() {
        return megan;
    }

    public void setMegan(Megan megan) {
        this.megan = megan;
    }


    public String getActionlist() {
        return actionlist;
    }

    public void setActionlist(String actionlist) {
        this.actionlist = actionlist;
    }

    public String getCalctype() {
        return calctype;
    }

    public void setCalctype(String calctype) {
        this.calctype = calctype;
    }


    public List<String> getModel() {
        return model;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }

    public Cmaq getCmaq() {
        return cmaq;
    }

    public void setCmaq(Cmaq cmaq) {
        this.cmaq = cmaq;
    }

}