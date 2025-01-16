package com.eugenedevv.bigqueryPOC.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class BigQueryConfig {
    @Value("${google.cloud.credentials.location}")
    private String bigQueryFile;
    @Value("${google.cloud.project-id}")
    private String projectId;
    @Value("${google.cloud.dataset-id}")
    private String datasetId;

    @Bean
    public BigQuery bigQuery() throws java.io.IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(bigQueryFile));
        return BigQueryOptions.newBuilder()
                .setLocation("africa-south1")
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
