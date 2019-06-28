package utils;

import us.codecraft.webmagic.Page;
import java.util.ArrayList;
import java.util.HashMap;

public class getBaseInfo {
    public static void BaseInfo(Page page, String[] s){
        //存储基本信息
        HashMap<String,String> baseInfo = new HashMap<String, String>();
        //存储标签信息
        ArrayList<String> label=new ArrayList<String>();
        int count =0;
        //将基本信息添加到baseInfo中
        for (String i:s){
            count++;
            if(i.startsWith("标签")){
                break;
            }
            String[] info= i.split(":");
            if(info.length==1||info.length==0){
                continue;
            }
            baseInfo.put(info[0],info[1]);
        }
        //将标签信息添加到label中
        if((count-1)!=s.length){
            for(int i=count;i<s.length;i++){
                label.add(s[i]);
            }
        }
        //目前将label也加入到baseInfo里面
        baseInfo.put("标签",label.toString());
        page.putField("基础信息",baseInfo.toString());

    }
}
