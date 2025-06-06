package com.kob.botrunningsystem.service.utils;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId,String botCode,String input) {
        lock.lock();
        try {
            Bot bot = new Bot(userId, botCode, input);
            bots.add(bot);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000,bot);
    }

    @Override
    public void run() {
        while(true) {
            lock.lock();
            if(bots.isEmpty()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else{
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot); // 比较耗时,可能要几秒钟，故先解锁
            }
        }
    }
}
