package com.lhl.mysql.export.mapper;

import com.lhl.mysql.export.bean.Column;
import com.lhl.mysql.export.bean.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExportMapper {

    List<Table> selectAllTables(String dbName);

    List<Column> selectColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);

}
