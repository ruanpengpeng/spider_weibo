package pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import utils.getBaseInfo;

import java.util.ArrayList;
import java.util.List;


public class TestSpider implements PageProcessor {
    private Site site = Site.me()
            .setCycleRetryTimes(3)
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setSleepTime(100)
            .setCharset("utf-8")
            .setDomain("weibo.cn")
            .addHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
            .addCookie("haha","_T_WM=82581848083; WEIBOCN_WM=3349; H5_wentry=H5; backURL=https%3A%2F%2Fm.weibo.cn%2Fprofile%2F5239863155%3Fsudaref%3Dm.weibo.cn%26display%3D0%26retcode%3D6102; _T_WL=1; _WEIBO_UID=7208272402; SCF=Alz5Hd_D3O7ohwIzEO5NUeRDn7A3iJgTAxnsgOUnkJqCwFc-5H_EKrpKZUofGTI4lisT2xK8VoBkcztQM5BRe8A.; SUB=_2A25wFzf4DeRhGeFM61oT9yzIyz6IHXVT-FmwrDV6PUJbkdAKLVDgkW1NQPhKWIy6da_eNkf0g8YoLsm4hSURkWOe; SUHB=0QSJEjJoaiya0F; SSOLoginState=1561544616");

    //解析单个用户资料的信息
    public void process(Page page) {
        Object[] all=page.getHtml().xpath("body/div").all().toArray();
        //如何没有基本信息那么直接跳过本页的爬取
        if(!all[4].toString().replaceAll("<.*?>|\n","").trim().equals("基本信息")){
            page.setSkip(true);
        }
        //解析基本信息并储存,目前只获取基本的信息，后续可以添加学历以及工作经验
        getBaseInfo.BaseInfo(page,all[5].toString().
                replaceAll("<.*?>|&nbsp|&gt|\n|;|\r","").split(" "));
        //添加后续的url
        List<String> urls = new ArrayList<String>();
        //随机添加1000个
        for(int i=0;i<20;i++){
            long a=(long)(Math.random()*(8999999999L)+1000000000L);
            urls.add("https://weibo.cn/"+a+"/info");
        }
        page.addTargetRequests(urls);
    }
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //添加domain的url
        us.codecraft.webmagic.Spider.create(new TestSpider())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .addUrl("https://weibo.cn/6373917807/info").
                addPipeline(new JsonFilePipeline("D:\\webmagic\\")).thread(5).run();

    }
}
