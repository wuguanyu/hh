package com.makenv.model.mc.tools;

import com.makenv.model.mc.core.util.FileUtil;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;

/**
 * Created by alei on 2017/3/9.
 */
public class MessageTool {
  public static void main(String[] args) throws IOException {
    String queue = "receive_queue_name";
    Jedis jedis = new Jedis("166.111.42.46", 16379);
    jedis.auth("123456");
    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model_1_4.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model-all.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model-wrf.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model-meic.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model-mcip.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\start-model-cmaq.json"));
//    String content = FileUtil.readLocalFile(new File("samples\\api\\BM\\test\\create-domain-1.json"));
    jedis.lpush(queue, content);
  }
}
