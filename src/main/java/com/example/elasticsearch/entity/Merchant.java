package com.example.elasticsearch.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Data
@Repository
public class Merchant {
    private String merchant_id;
    private long create_time;
    private String location_city;
    private String location_province;
    private String location_region;
    private String merchant_mark;
    private String merchant_name;
    private String merchant_no;

}


