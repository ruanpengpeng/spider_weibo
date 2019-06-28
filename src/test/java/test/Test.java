package test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test implements PageProcessor {
    private Site site = Site.me()
            .setCycleRetryTimes(3)
            .setRetryTimes(3)
            .setSleepTime(300)
            .setCharset("utf-8")
            .setDomain("weibo.cn")
            .addHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
            .addCookie("haha","_T_WM=82581848083; WEIBOCN_WM=3349; H5_wentry=H5; backURL=https%3A%2F%2Fm.weibo.cn%2Fprofile%2F5239863155%3Fsudaref%3Dm.weibo.cn%26display%3D0%26retcode%3D6102; _T_WL=1; _WEIBO_UID=7208272402; SCF=Alz5Hd_D3O7ohwIzEO5NUeRDn7A3iJgTAxnsgOUnkJqCwFc-5H_EKrpKZUofGTI4lisT2xK8VoBkcztQM5BRe8A.; SUB=_2A25wFzf4DeRhGeFM61oT9yzIyz6IHXVT-FmwrDV6PUJbkdAKLVDgkW1NQPhKWIy6da_eNkf0g8YoLsm4hSURkWOe; SUHB=0QSJEjJoaiya0F; SSOLoginState=1561544616");

    //解析单个用户资料的信息
    public void process(Page page) {
        Object[] all=page.getHtml().xpath("body/div").all().toArray();
        //存储基本信息
        HashMap<String,String> baseInfo = new HashMap<String, String>();
        //存储标签信息
        ArrayList<String> label=new ArrayList<String>();
        int count =0;
        //System.out.println(!all[4].toString().replaceAll("<.*?>|\n","").trim().equals("基本信息"));
        /*if(all[4].toString().replaceAll("<.*?>|\n","").trim()!="基本信息"){
            System.out.println("all[4]的值不为基本信息");
        }*/

        String[] s = all[5].toString().replaceAll("<.*?>|&nbsp|&gt|\n|;|\r","").split(" ");
        System.out.println(s.length);
        //将基本信息添加到baseInfo中
       /*for (String i:s){
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
        }*/


    }

    public Site getSite() {
        return site;
    }

    //加载用户的资料信息，程序入口
    public static void main(String[] args) {
        //添加domain的url
        us.codecraft.webmagic.Spider.create(new Test()).addUrl("https://weibo.cn/6373917807/info").thread(5).run();

    }
}
