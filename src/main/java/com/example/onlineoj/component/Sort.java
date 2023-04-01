package com.example.onlineoj.component;


import com.example.onlineoj.dao.TimeMapper;
import com.example.onlineoj.model.Time;
import org.springframework.stereotype.Component;

@Component
public class Sort {
    private final TimeMapper timeMapper;

    public Sort(TimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    public Integer getLow(int id,int duration){
        Integer cnt = timeMapper.selectMin(id, duration);
        return cnt;
    }
    public Integer getHigh(int id,int duration){
        Integer cnt = timeMapper.selectMax(id, duration);
        return cnt;
    }
    public Time find(int id,int duration){
        Time t = timeMapper.selectById(id,duration);
        return t;
    }
    public String operation(int id,int duration){
        int low = getLow(id,duration);
        int high = getHigh(id,duration);
        System.out.println("low="+low + " high "+high);
        Time t = find(id, duration);
        int cur = 0;
        if(t!=null){
            cur = t.getCnt();
            t.setCnt(cur+1);
            timeMapper.update(t);
        }else{
            Time t1 = new Time();
            t1.setId(id);
            t1.setDuration(duration);
            timeMapper.add(t1);
        }
        cur++;
        double ans = (cur+high)*100.0/(high+low+cur);
        return "您已击败 "+ans+" %的提交!";
    }
}
