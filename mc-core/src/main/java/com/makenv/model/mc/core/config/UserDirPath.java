package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class UserDirPath {
  private String dirPath;
  private DomainRangeDir domainid;

  public DomainRangeDir getDomainid() {
    return domainid;
  }

  public void setDomainid(DomainRangeDir domainid) {
    this.domainid = domainid;
  }


  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

}
