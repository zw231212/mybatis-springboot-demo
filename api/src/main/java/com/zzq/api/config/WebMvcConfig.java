package com.zzq.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig  extends WebMvcConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

  @Value("${static.filePath}")
  private String staticFilePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //文件磁盘图片url 映射
    // 配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
    String userDir = System.getProperty("user.dir");
    String datasetDir = "file:"+userDir +"\\";
    logger.info("静态文件资源目录是："+datasetDir+"，匹配的模式是："+staticFilePath);
    registry.addResourceHandler(staticFilePath).addResourceLocations(datasetDir);
  }
}
