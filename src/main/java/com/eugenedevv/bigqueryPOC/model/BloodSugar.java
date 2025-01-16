package com.eugenedevv.bigqueryPOC.model;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodSugar {
    private String id;
    private String measurementTime;
    private Float value;
    private String notes;

    public void setId(String id) {
        this.id = id;
    }
    public void setValue(Float value) {
        this.value = value;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setMeasurementTime(String measurementTime) {
        this.measurementTime = measurementTime;
    }

    public String getMeasurementTime() {
        return measurementTime;
    }

    public String getId() {
        return id;
    }
    public Float getValue() {
        return value;
    }

    public String getNotes() {
        return notes;
    }
}
