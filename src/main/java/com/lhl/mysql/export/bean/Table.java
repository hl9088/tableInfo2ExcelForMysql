package com.lhl.mysql.export.bean;

import java.util.List;

public class Table {

    /**
     * 表名
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    private List<Column> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
