package com.ade.exp.activemq.embed;

import org.apache.activemq.broker.BrokerService;
//import org.apache.activemq.broker.region.policy.PolicyEntry;
//import org.apache.activemq.broker.region.policy.PolicyMap;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.apache.activemq.store.kahadb.KahaDBStore;
//
//import java.io.File;
//import java.io.IOException;

/**
 *
 * Created by liyang on 16-7-28.
 */
public class BrokerTest {

//    private void setKaha(BrokerService broker) {
//        File dataFileDir = new File(System.getProperty("user.dir") + "/kahadb");
//        System.out.println(dataFileDir.getPath());
//        KahaDBStore kaha = new KahaDBStore();
//        kaha.setDirectory(dataFileDir);
//        // Using a bigger journal file
//        kaha.setJournalMaxFileLength(1024*100);
//        // small batch means more frequent and smaller writes
//        kaha.setIndexWriteBatchSize(100);
//        // do the index write in a separate thread
//        kaha.setEnableIndexWriteAsync(true);
//        try {
//            broker.setPersistenceAdapter(kaha);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void start() {
        try {
            BrokerService broker = new BrokerService();
            broker.addConnector("auto://localhost:61616");

            // 设置持久化模式 默认使用kahadb
            broker.setPersistent(true);
            // 可以手动配置参数
//            setKaha(broker);
//            broker.setUseJmx(false); //启用JMX监控


            // 启用Advisory指定队列的消息监控
//            PolicyMap   policy = new PolicyMap();
//            PolicyEntry entry  = new PolicyEntry();
//            entry.setAdvisoryForConsumed(true);
//            policy.put(new ActiveMQQueue(">"), entry);
//            broker.setDestinationPolicy(policy);
            broker.start();

            synchronized (this) {
                this.wait();
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        new BrokerTest().start();
    }

}
