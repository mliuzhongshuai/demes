package com.example.demo.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * 布隆过滤器，用于缓存穿透，诈骗提醒，
 *
 * @author liuzhongshuai
 *         Created on 2018/4/26.
 */
public class DemoBloomFilter {


    /**
     * 应用场景：缓存穿透，垃圾短信号码等
     */
    public static void main( String[] args) {
        //创建bloomFilter，保存 10000条数据，误伤率 为 1%
        int size=1000000;
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), size, 0.01);
        //存入标识的信息
        for (int i=0;i<size;i++){
            //代表存在的数据
            bloomFilter.put(UUID.randomUUID().toString());
        }
        String value=UUID.randomUUID().toString();
        //判断该value 是否在 filter中存在
        bloomFilter.mightContain(value);
        Long count=bloomFilter.approximateElementCount();
        System.out.println(count);

    }


}
