package com.example.elasticsearch;



import com.example.elasticsearch.service.MerchantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.elasticsearch.scroll.ScrollHandler;
import java.util.*;
import org.frameworkset.elasticsearch.scroll.HandlerInfo;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired MerchantService merchantService;
    @Test
    public void testBbossESStarter() {
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
                public void handle(ESDatas<Map> esDatas, HandlerInfo handlerInfo) throws Exception {
                    List<Map> dataList = esDatas.getDatas();
                    //System.out.println("TotalSize:" + esDatas.getTotalSize());

                    for (int i = 0; i < dataList.size(); i++) {
                        //System.out.println("第" + i + "个");
                        HashMap<String, Double> dataMap = (HashMap<String, Double>) dataList.get(i);
                        //System.out.println(dataMap);
                        //System.out.println("---------"+dataList.get(i).keySet());
                        //Set<String> key =  dataList.get(i).keySet();
                        for (Object key : dataList.get(i).keySet()
                        ) {

                            System.out.println(dataMap.get(key));
                        }

                    }
//                if(dataList != null) {
//                    System.out.println("dataList.size:" + dataList.size());
//                }
//                else
//                {
//                   System.out.println("dataList.size:0");
//                }
                    //do something other such as do a db query.
                    //SQLExecutor.queryList(Map.class,"select * from td_sm_user");
                }
            }, Map.class);
//            Map<String,Object> params = new HashMap<String,Object>();
//            params.put("expired",3600);
//            System.out.println(clientUtil.search("bill/landi/_search","ismember",params,Map.class).getSearchHits());
        }else{
            System.out.println("索引不存在");
        }

    }

    @Test
    public void changeData(){
        merchantService.change();

    }



}
