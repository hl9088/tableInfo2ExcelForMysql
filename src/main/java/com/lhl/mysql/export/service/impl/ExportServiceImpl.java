package com.lhl.mysql.export.service.impl;

import com.lhl.mysql.export.bean.Column;
import com.lhl.mysql.export.bean.Table;
import com.lhl.mysql.export.mapper.ExportMapper;
import com.lhl.mysql.export.service.ExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportMapper exportMapper;


    @Override
    public String ExportTableDefine(String dbName, String filePath) {
        // 先查询出库里所有的表列表
        List<Table> tableList = exportMapper.selectAllTables(dbName);
        for (Table table : tableList) {
            List<Column> columnList = exportMapper.selectColumns(dbName, table.getName());
            table.setColumns(columnList);
        }

        int rowNum = 0;
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            Sheet sheet = workbook.createSheet(dbName);
            sheet.setFitToPage(true);
            sheet.setColumnWidth(1, 1024*4);
            sheet.setColumnWidth(2, 1024*4);
            sheet.setColumnWidth(3, 1024*4);
            for (Table table : tableList) {
                // 单独一行写入表名
                Row row = sheet.createRow(rowNum++);
                row.createCell(1).setCellValue(table.getName());
                row.createCell(2).setCellValue(table.getComment());
                // 写入一行标题行
                Row title = sheet.createRow(rowNum++);
                title.createCell(0).setCellValue("序号");
                title.createCell(1).setCellValue("字段名");
                title.createCell(2).setCellValue("名称");
                title.createCell(3).setCellValue("类型");
                title.createCell(4).setCellValue("是否可空");
                title.createCell(5).setCellValue("key");
                title.createCell(6).setCellValue("备注");
                setCellStyle(title, cellStyle);

                List<Column> columnList = table.getColumns();
                int size = columnList.size();
                for (int i = 0; i < size; i++) {
                    Column column = columnList.get(i);
                    Row r = sheet.createRow(rowNum++);
                    r.createCell(0).setCellValue(column.getPosition());
                    r.createCell(1).setCellValue(column.getName());
                    r.createCell(2).setCellValue(column.getComment());
                    r.createCell(3).setCellValue(column.getDataType());
                    r.createCell(4).setCellValue(column.getIsNull());
                    r.createCell(5).setCellValue(column.getKey());
                    r.createCell(6);
                    setCellStyle(r, cellStyle);
                }
                rowNum++;
            }

            File file = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            workbook.write(bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCellStyle(Row row, CellStyle cellStyle) {
        for (int i = 0; i < 7; i++) {
            row.getCell(i).setCellStyle(cellStyle);
        }
    }
}
