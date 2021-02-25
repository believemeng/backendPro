package com.dspread.august;

//import io.github.yedaxia.apidocs.Docs;
//import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableScheduling
public class AugustApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AugustApplication.class)
                .properties(getProperties());
    }

    public static void main(String[] args) {
//        DocsConfig config= new DocsConfig();
//        config.setProjectPath("C:\\Users\\kangm\\Desktop\\dspread"); // 项目根目录
//        config.setProjectName("dspread"); // 项目名称
//        config.setApiVersion("V1.0");      // 声明该API的版本
//        config.setDocsPath("src\\main\\resources\\templates"); // 生成API 文档所在目录
//        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
//        Docs.buildHtmlDocs(config); // 执行生成文档
        SpringApplication.run(AugustApplication.class, args);
    }

    static Properties getProperties(){
        Properties props = new Properties();
        props.put("spring.config.location", "classpath:august/");
        return props;
    }
}
