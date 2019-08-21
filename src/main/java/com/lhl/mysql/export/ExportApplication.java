package com.lhl.mysql.export;

import com.lhl.mysql.export.service.ExportService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lhl.mysql.export.mapper")
public class ExportApplication implements CommandLineRunner {

    @Value("${mysql.db.name}")
    private String dbName;

    @Value("${mysql.db.filePath}")
    private String filePath;

    @Autowired
    private ExportService exportService;

    public static void main(String[] args) {
        SpringApplication.run(ExportApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        exportService.ExportTableDefine(dbName, filePath);
    }
}
