package com.makenv.model.mc.server.message.task.bean;

/**
 * Created by alei on 2017/3/11.
 */
public class CmaqBean {
  private String start_date;
  private int time_difference;
  private int run_days;
  private String cmaq_version;
  private String scripts_path;
  private String cmaq_build_path;
  private String GRIDDESC_path;
  private String mcip_output_path;
  private String meic_output_path;
  private String bcon_output_path;
  private String base_cmaq_output_path;
  private String cmaq_output_path;
  private int max_dom;
  private int run_type;
  private int debug;

  public String getStart_date() {
    return start_date;
  }

  public void setStart_date(String start_date) {
    this.start_date = start_date;
  }

  public int getTime_difference() {
    return time_difference;
  }

  public void setTime_difference(int time_difference) {
    this.time_difference = time_difference;
  }

  public int getRun_days() {
    return run_days;
  }

  public void setRun_days(int run_days) {
    this.run_days = run_days;
  }

  public String getCmaq_version() {
    return cmaq_version;
  }

  public void setCmaq_version(String cmaq_version) {
    this.cmaq_version = cmaq_version;
  }

  public String getScripts_path() {
    return scripts_path;
  }

  public void setScripts_path(String scripts_path) {
    this.scripts_path = scripts_path;
  }

  public String getCmaq_build_path() {
    return cmaq_build_path;
  }

  public void setCmaq_build_path(String cmaq_build_path) {
    this.cmaq_build_path = cmaq_build_path;
  }

  public String getGRIDDESC_path() {
    return GRIDDESC_path;
  }

  public void setGRIDDESC_path(String GRIDDESC_path) {
    this.GRIDDESC_path = GRIDDESC_path;
  }

  public String getMcip_output_path() {
    return mcip_output_path;
  }

  public void setMcip_output_path(String mcip_output_path) {
    this.mcip_output_path = mcip_output_path;
  }

  public String getMeic_output_path() {
    return meic_output_path;
  }

  public void setMeic_output_path(String meic_output_path) {
    this.meic_output_path = meic_output_path;
  }

  public String getBcon_output_path() {
    return bcon_output_path;
  }

  public void setBcon_output_path(String bcon_output_path) {
    this.bcon_output_path = bcon_output_path;
  }

  public String getBase_cmaq_output_path() {
    return base_cmaq_output_path;
  }

  public void setBase_cmaq_output_path(String base_cmaq_output_path) {
    this.base_cmaq_output_path = base_cmaq_output_path;
  }

  public String getCmaq_output_path() {
    return cmaq_output_path;
  }

  public void setCmaq_output_path(String cmaq_output_path) {
    this.cmaq_output_path = cmaq_output_path;
  }

  public int getMax_dom() {
    return max_dom;
  }

  public void setMax_dom(int max_dom) {
    this.max_dom = max_dom;
  }

  public int getRun_type() {
    return run_type;
  }

  public void setRun_type(int run_type) {
    this.run_type = run_type;
  }

  public int getDebug() {
    return debug;
  }

  public void setDebug(int debug) {
    this.debug = debug;
  }
}