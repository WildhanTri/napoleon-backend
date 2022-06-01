package com.example.napoleon.payload.request;

import com.example.napoleon.model.Reportline;

public class ReportDataRequest {
    private String uuid;
    private String value;
    private String note;
    private Reportline reportline;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Reportline getReportline() {
        return reportline;
    }
    public void setReportline(Reportline reportline) {
        this.reportline = reportline;
    }
}
