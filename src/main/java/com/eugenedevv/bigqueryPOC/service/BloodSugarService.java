package com.eugenedevv.bigqueryPOC.service;

import com.eugenedevv.bigqueryPOC.client.BigQueryClient;
import com.eugenedevv.bigqueryPOC.model.BloodSugar;
import com.google.cloud.bigquery.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

@Service
public class BloodSugarService {

    @Autowired
    private BigQueryClient bigQueryClient;

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.dataset-id}")
    private String datasetId;

    private String tableName;

    @PostConstruct
    private void init() {
        this.tableName = String.format("%s.blood_sugar_measurements", datasetId);
    }

    public void saveBloodSugar(BloodSugar bloodSugar) {
        bloodSugar.setId(UUID.randomUUID().toString().replace("-", ""));

        String query = String.format(
                "INSERT INTO %s (id, measurement_time, value, notes) VALUES ('%s', TIMESTAMP('%s'), %f, '%s')",
                tableName, bloodSugar.getId(), bloodSugar.getMeasurementTime(), bloodSugar.getValue(), bloodSugar.getNotes()
        );
        bigQueryClient.query(query);
    }

    public List<BloodSugar> getAllBloodSugarRecords() {
        String query = String.format(
                "SELECT * FROM %s", tableName
        );

        TableResult result = bigQueryClient.query(query);
        List<BloodSugar> bloodSugarRecords = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            String id = row.get("id").getStringValue();
            long timestampMicros = row.get("measurement_time").getTimestampValue();
            Float value = row.get("value").getNumericValue().floatValue();
            String notes = row.get("notes").getStringValue();
            BloodSugar bloodSugar = new BloodSugar();
            bloodSugar.setId(id);
            bloodSugar.setMeasurementTime(String.valueOf(timestampMicros));
            bloodSugar.setValue(value);
            bloodSugar.setNotes(notes);

            bloodSugarRecords.add(bloodSugar);
        });
        return bloodSugarRecords;
    }
}
