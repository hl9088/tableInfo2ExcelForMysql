package com.lhl.mysql.export.service;

public interface ExportService {

    /**
     * 将mysql数据库中某个库里面的所有表信息导出到一个excel中
     *
     * @param dbName   库名称
     * @param filePath excel路径
     * @return
     */
    String ExportTableDefine(String dbName, String filePath);
}
