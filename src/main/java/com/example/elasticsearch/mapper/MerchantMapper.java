package com.example.elasticsearch.mapper;
import com.example.elasticsearch.entity.Merchant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Administrator
 */
@Mapper
public interface MerchantMapper {
    @Insert("insert into t_merchant (merchant_id,create_time,location_city,location_province,location_region,merchant_mark,merchant_name,merchant_no) values(#{merchant_id},#{create_time},#{location_city},#{location_province},#{location_region},#{merchant_mark},#{merchant_name},#{merchant_no})")
    void insert(Merchant merchant);

}
