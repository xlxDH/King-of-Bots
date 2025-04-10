package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String discription = data.get("discription");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if(title==null || title.length()==0){
            map.put("error_message","标题不能为空");
            return map;
        }

        if(title.length()>100){
            map.put("error_message","标题长度不能超过100个字符");
            return map;
        }

        if(discription==null || discription.length()==0){
            discription = "这个人很懒,什么也没写";
        }

        if(discription.length()>300){
            map.put("error_message","Bot的描述不能超过300个字符");
            return map;
        }

        if(content==null||content.length()==0){
            map.put("error_message","Bot的代码不能为空");
            return map;
        }

        if(content.length()>10000){
            map.put("error_message","Bot的内容不能超过10000个字符");
            return map;
        }

        Bot bot = botMapper.selectById(bot_id);

        if(bot==null){
            map.put("error_message","该Bot不存在或已被删除");
            return map;
        }

        if(!Objects.equals(bot.getUserId(), user.getId())){
            map.put("error_message","没有权限修改该Bot");
            return map;
        }
        Date now = new Date();
        Bot new_bot = new Bot(
                bot.getId(),
                bot.getUserId(),
                title,
                discription,
                content,
                bot.getCreatetime(),
                now
        );

        botMapper.updateById(new_bot);
        map.put("error_message","success");
        return map;
    }
}
