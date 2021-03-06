package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.config.Model;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import com.makenv.model.mc.server.message.task.bean.McipBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2017/3/8.
 */
public class McipTask extends AbstractCmaqTask {
  private Logger logger = LoggerFactory.getLogger(McipTask.class);
  private McipBean mcipBean;
  private String runDir, renvPath, dataDir, wrfPath;

  public McipTask(ModelStartBean modelStartBean, McConfigManager configManager, String messageId) throws IOException {
    super(modelStartBean, configManager, messageId);
    mcipBean = new McipBean();
  }

  protected boolean checkParams() {
    return super.checkParams();
  }

  private boolean processDirectory() {
    String base = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getRun().getMcip().getDirPath();
    runDir = processPath(base);
    runDir = String.format("%s%s%s", runDir, File.separator, System.currentTimeMillis());
    renvPath = String.format("%s%s%s", runDir, File.separator, Constant.MODEL_RENV_FILE);

    dataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMcip().getDirPath();
    dataDir = processPath(dataDir);
    wrfPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrf().getDirPath();
    wrfPath = processPath(wrfPath);

    if (modelStartBean.getCommon().getDatatype().equals(Constant.GLOBAL_TYPE_GFS)) {
      String initialTime = LocalTimeUtil.ldToUcTime(modelStartBean.getCommon().getPathdate(), configManager.getSystemConfig().getModel().getStart_hour());
      dataDir = String.format("%s%s%s", dataDir, File.separator, initialTime);
      wrfPath = String.format("%s%s%s", wrfPath, File.separator, initialTime);
    }
    if (!FileUtil.checkAndMkdir(runDir)) {
      logger.error(StringUtil.formatLog("create dir failed", runDir));
      return false;
    }
    if (!FileUtil.checkAndMkdir(dataDir)) {
      logger.error(StringUtil.formatLog("create dir failed", dataDir));
      return false;
    }
    return true;
  }

  private void createMcipBean() throws IOException {
    mcipBean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    mcipBean.setWrf_start_hour(startHour);
    mcipBean.setTime_difference(timeDiff);
    mcipBean.setGlobal(modelStartBean.getCommon().getDatatype());
    mcipBean.setRun_days((int) LocalTimeUtil.between(startDate, endDate) + 1);
    mcipBean.setScripts_path(scriptPath);
    mcipBean.setCmaq_build_path(cmaqBuildPath);
    mcipBean.setGeogrid_output_path(geogridOutputPath);
    mcipBean.setWrf_output_path(wrfPath);
    mcipBean.setMcip_output_path(dataDir);
    TaskDomain domain = getTaskDomain();
    mcipBean.setCoordName(domain.getCommon().getCoord_Name());
    mcipBean.setCmaq_version(domain.getCmaq().getVersion());
    mcipBean.setRef_lat(domain.getCommon().getRef_lat() + "");
    mcipBean.setMax_dom(domain.getCommon().getMax_dom());
    mcipBean.setBtrim(domain.getMcip().getBtrim());
    mcipBean.setCTMLAYS(domain.getMcip().getCtmlays());
    mcipBean.setDebug(debugLevel);
    Model model = configManager.getSystemConfig().getModel();
    mcipBean.setReinit_cycle_days(model.getReinit_cycle_days());
    mcipBean.setReinit_judge_tool(model.getReinit_judge_tool());
    mcipBean.setReinit_origin_date(model.getReinit_origin_date());
  }

  private boolean buildRenv() {
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getRenv_mcip_sh(), "mcipBean", mcipBean);
    try {
      FileUtil.writeAppendLocalFileInLinux(new File(renvPath), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private boolean buildCsh() {
    Map<String, Object> params = new HashMap<>();
    params.put("mcip_run_dir", runDir);
    params.put("mcip_script", configManager.getSystemConfig().getCsh().getModule_mcip_csh());
    params.put("renv_scrpit", renvPath);
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getCsh_mcip(), params);
    try {
      FileUtil.writeAppendLocalFileInLinux(getModelRunFile(), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  @Override
  protected boolean doHandle() {
    try {
      createMcipBean();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return buildRenv() && buildCsh();
  }
}
