package com.zhaizhengqing.try_redission;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TryRedissonTest {
    ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException, ExecutionException, InterruptedException {
        TryRedissonTest tryRedisson = new TryRedissonTest();
//        tryRedisson.normal();
//        tryRedisson.pipline();
        tryRedisson.ao();
    }

    public void basic() throws JsonProcessingException {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.56.121:6379").addNodeAddress("redis://192.168.56.122:6379").addNodeAddress("redis://192.168.56.123:6379").setPassword("foobared");
        RedissonClient redisson = Redisson.create(config);


        RBucket<String> john = redisson.getBucket("John", StringCodec.INSTANCE);
        john.set("Alice");

        String o = john.get();
        System.out.println(o);
        RKeys rKeys = redisson.getKeys();
        Iterable<String> keys = rKeys.getKeysByPattern("*");
        System.out.println(objectMapper.writeValueAsString(keys));


        RBucket<Object> bob = redisson.getBucket("Bob", StringCodec.INSTANCE);
        bob.set("Ali2");
//        System.out.println(bob.getAndDelete());
        System.out.println(bob.get());

        redisson.shutdown();
    }

    public void normal() throws JsonProcessingException {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.56.121:6379").addNodeAddress("redis://192.168.56.122:6379").addNodeAddress("redis://192.168.56.123:6379").setPassword("foobared");
        RedissonClient redisson = Redisson.create(config);
        RBitSet bs = redisson.getBitSet("{a}bs");
        bs.delete();
        long time = new Date().getTime();
        for (Long i = 0l; i < 10000; i++) {
            bs.set(i, i % 2 == 0);
        }
        System.out.println(bs.get(1));
        System.out.println(bs.get(2));
        long d = new Date().getTime() - time;
        System.out.println(d);
        redisson.shutdown();
    }

    public void pipline() throws JsonProcessingException, ExecutionException, InterruptedException {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.56.121:6379").addNodeAddress("redis://192.168.56.122:6379").addNodeAddress("redis://192.168.56.123:6379").setPassword("foobared");
        RedissonClient redisson = Redisson.create(config);

        for (int k = 0; k < 5; k++) {
            String key = String.format("{a}bs%d", k);
            RBitSet bs = redisson.getBitSet(key);
            bs.delete();
            System.out.println(bs.get(1));
            System.out.println(bs.get(2));
            long time = new Date().getTime();
            long h = 0;
            for (int j = 0; j < 9; j++) {
                RBatch batch = redisson.createBatch();
                RBitSetAsync bitSet = batch.getBitSet(key);
                for (Long i = 0l; i < 1000000; i++) {
                    bitSet.setAsync(h, Math.floor(Math.random() * 10) % 2 == 0);
                    h += 1;
                }
                BatchResult<?> execute = batch.execute();
            }
            long d = new Date().getTime() - time;
            System.out.println(d);
        }

        redisson.shutdown();
    }

    public void ao() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.56.121:6379").addNodeAddress("redis://192.168.56.122:6379").addNodeAddress("redis://192.168.56.123:6379").setPassword("foobared");
        RedissonClient redisson = Redisson.create(config);
        ArrayList<RBitSet> arrayList = new ArrayList<>();
        long time = new Date().getTime();
        for (int i = 0; i < 5; i++) {
            arrayList.add(redisson.getBitSet(String.format("{a}bs%d", i)));
            System.out.println(String.format("%d %d", arrayList.get(i).cardinality(), arrayList.get(i).size()));
        }
        RBitSet bitSet = redisson.getBitSet("{a}a0");
        bitSet.delete();
        System.out.println(String.format("%d %s", bitSet.cardinality(), "0"));
        bitSet.or("{a}bs0");
        bitSet.not();
        System.out.println(String.format("%d %s", bitSet.cardinality(), "1"));

        bitSet.and("{a}bs1");
        System.out.println(String.format("%d %s", bitSet.cardinality(), "2"));

        bitSet.and("{a}bs2");
        System.out.println(String.format("%d %s", bitSet.cardinality(), "3"));
        bitSet.and("{a}bs3");
        System.out.println(String.format("%d %s", bitSet.cardinality(), "4"));
        RBitSet bitSet1 = redisson.getBitSet("{a}a1");
        bitSet1.delete();
        System.out.println(String.format("%d %s", bitSet1.cardinality(), "0"));
        bitSet1.or("{a}bs4");
        System.out.println(String.format("%d %s", bitSet1.cardinality(), "5"));
        bitSet1.not();
        System.out.println(String.format("%d %s", bitSet1.cardinality(), "6"));
        bitSet.and("{a}a1");
        System.out.println(String.format("%d %s", bitSet.cardinality(), "7"));
        long d = new Date().getTime() - time;
        System.out.println(d);
        redisson.shutdown();
    }
}
