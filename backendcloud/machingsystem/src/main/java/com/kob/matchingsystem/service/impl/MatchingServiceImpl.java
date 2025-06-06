package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {

    public final static MatchingPool mappingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        mappingPool.addPlayer(userId, rating , botId);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("Remove Player "  + userId);
        mappingPool.removePlayer(userId);
        return "remove player success";
    }
}
