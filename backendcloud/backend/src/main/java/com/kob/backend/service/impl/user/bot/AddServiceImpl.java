package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

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

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(),title,discription,content,now,now );

        botMapper.insert(bot);
        map.put("error_message","success");

        return map;
    }
}
