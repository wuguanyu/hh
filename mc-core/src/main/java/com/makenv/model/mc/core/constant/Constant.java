package com.makenv.model.mc.core.constant;

/**
 * Created by wgy on 2017/2/28.
 */
public class Constant {

  public static final String[] FILE_HOURS = {"00", "06", "12", "18"};
  public static final String UNGRIB_FILE_PREFIX = "FILE";
  public static final String GRIDNAME = "China_d%02d";

  public static final String NAMELIST_IPXWRF_TEMPLATE = "namelist.ipxwrf.template";
  public static final String NAMELIST_OA_TEMPLATE = "namelist.oa.template";
  public static final String NAMELIST_WPS_GEOGRID_TEMPLATE = "namelist.wps.geogrid.template";
  public static final String NAMELIST_WRF_TEMPLATE = "namelist.wrf.template";
  public static final String NAMELIST_WPS_METGRID_TEMPLATE = "namelist.wps.metgrid.template";
  public static final String NAMELIST_WPS_UNGRIB_TEMPLATE = "namelist.wps.ungrib.template";
  public static final String MEIC_CONF = "meicd%02d_%s.conf";
  public static final String MEIC_CONF_TEMPLATE = MEIC_CONF + ".template";

  public static final String GEOGRID_SCRIPT_FILE = "invoke-geogrid";

  public static final String DOMAIN_RENV_FILE = "renv-domain.csh";

  public static final String TORQUE_LOG_INFO = "torque-info.log";
  public static final String TORQUE_LOG_ERROR = "torque-error.log";
  public static final String MODEL_RENV_FILE = "renv";
  public static final String MODEL_SCRIPT_FILE = "invoke";

  public static final String GLOBAL_TYPE_FNL = "fnl";
  public static final String GLOBAL_TYPE_GFS = "gfs";

  public static final String DOMAIN_JSON = "domain.json";

  public static final String MEIC_CACHE_TYPE = "cache";
  public static final String MEIC_SERVER_TYPE = "server";

  public static final String ACTIONLIST_PS = "ps-action.csv";
  public static final String ACTIONLIST_SS = "ss-action.csv";
  public static final String MEIC_JSON = "meic.json";

}
