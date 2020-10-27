package com.example.elasticsearch.service;

import com.example.elasticsearch.entity.Merchant;
import com.example.elasticsearch.mapper.MerchantMapper;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.elasticsearch.scroll.HandlerInfo;
import org.frameworkset.elasticsearch.scroll.ScrollHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class MerchantService {

    @Autowired MerchantMapper merchantMapper;
    @Autowired Merchant merchant;

    public void change(){
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();

        //验证环境,获取es状态
        String result = clientUtil.executeHttp("_cluster/state?pretty",ClientInterface.HTTP_GET);
        //判读索引和表是否存在，false表示不存在，正常返回true表示存在
        boolean exist = clientUtil.existIndiceType("merchant","landi");
        System.out.println("index and type exist:"+exist);
        if(exist) {
            //计算索引bill下文档数
            long count = clientUtil.countAll("merchant");
            System.out.println("count:" + count);

            //获取索引bill下所有文档,提取数10000,使用 ScrollHandler 处理数据。
            clientUtil.searchAll("merchant", 10000, new ScrollHandler<Map>() {
                @Override
                public void handle(ESDatas<Map> esDatas, HandlerInfo handlerInfo) throws Exception {
                    List<Map> dataList = esDatas.getDatas();

                    for (int i = 0; i < dataList.size(); i++) {

                        HashMap<String, Object> dataMap = (HashMap<String, Object>) dataList.get(i);
                        for (Object key : dataList.get(i).keySet()) {
                            String k = (String) key;
                            String v = dataMap.get(key)+"";

                            switch (k){
                                case "merchantId":
                                    merchant.setMerchant_id(v);
                                    break;
                                case "merchantName":
                                    merchant.setMerchant_name(v);
                                    break;
                                case "merchantNo":
                                    merchant.setMerchant_no(v);
                                    break;
                                case "merchantMark":
                                    merchant.setMerchant_mark(v);
                                    break;
                                case "locationProvince":
                                    merchant.setLocation_province(v);
                                    break;
                                case "locationCity":
                                    merchant.setLocation_city(v);
                                    break;
                                case "locationRegion":
                                    merchant.setLocation_region(v);
                                    break;
                                case "createTime":
                                    Long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(v).getTime();
                                    merchant.setCreate_time(time);
                                    break;
                                default:break;


                            }

                        }
                        merchantMapper.insert(merchant);
                        System.out.println(merchant.toString());
                    }


                }
            }, Map.class);

        }else{
            System.out.println("索引不存在");
        }



    }
//    public void insert(Merchant merchant) {
//
//         merchantMapper.insert(merchant);
//    }
}
