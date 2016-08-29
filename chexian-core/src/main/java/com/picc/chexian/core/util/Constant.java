package com.picc.chexian.core.util;


public class Constant {
    // 运维人员与推广人员的预留ID
    public static String ADMIN_LEVEL0 = "2";
    public static String ADMIN_LEVEL1 = "3";
    public static String ADMIN_LEVEL2 = "4";
    public static String ADMIN_LEVEL3 = "5";
    // 掌通的渠道
    public static String SOURCE_ZHANGTONG = "zhangtong";
//    // 软猎的的渠道
//    public static String SOURCE_RUANLIE = "RUANLIE";
//    // 行者天下的渠道
//    public static String SOURCE_XINGZHETIANXIA = "XINGZHETIANXIA";
//    // 指盟的渠道
//    public static String SOURCE_ZHIMENG = "ZHIMENG";
    
    // 平台的类型
    public static Integer CLIENTTYPE_HTML = 0;
    public static Integer CLIENTTYPE_IOS = 1;
    public static Integer CLIENTTYPE_APPOFFLINE = 3;
    public static Integer CLIENTTYPE_APPONLINE = 4;
    public static Integer CLIENTTYPE_OTHER = 5; // 第三方接入商
    public static Integer CLIENTTYPE_OTHERWECHAT = 6;// 第三方微信接入商
    // 新手任务的APPID
    public static String NEW_TASK_APPID = "1234";
    // 系统版本类型
    public static String VERSION_TYPE_WECHAT = "wechat";
    public static String VERSION_TYPE_ANDROID = "android";
    public static String VERSION_TYPE_IOS = "ios";
    // 任务类型
    
    public static int TASKTYPE_FAST = 1;
    public static int TASKTYPE_SEARCH = 2;
    public static int TASKTYPE_AWARD = 3;
    public static int TASKTYPE_LANGYI = 4;
    public static int TASKTYPE_ASO = 5;
    public static int TASKTYPE_BATMOBI = 6;
    public static int TASKTYPE_WANPU = 7;
    public static int TASKTYPE_PREDUP = 8;
    public static int TASKTYPE_DIANRU = 9;

    /**
     * 礼包类型
     * 1 新手礼包
     * 2 普通礼包
     */
    public static int PACK_TYPE_NEWUSER = 1;
    public static int PACK_TYPE_NORMAL = 2;
    
    /**
     * 礼包点显示模式
     * 1 只展示新手礼包（新手礼包领取后该礼包点不再展示）
     * 2 只展示常规礼包
     * 3 新手礼包领取后展示常规礼包
     * 4 领取一次普通礼包后不再显示
     * 5 关闭
     */
    public static int DISPLAY_MODE_NEWUSER_ONLY = 1;
    public static int DISPLAY_MODE_NORMAL_ONLY = 2;
    public static int DISPLAY_MODE_NEWUSER_NORMAL = 3;
    public static int DISPLAY_MODE_NORMAL_ONCE = 4;
    public static int DISPLAY_MODE_OFF = 5;
    
    //精品推荐
    public static final String CHANNEL_RECOMMEND="recommend";
    //首页宣传图
    public static final String CHANNEL_PROPAGANDA="propaganda";
    //首页列表
    public static final String CHANNEL_INDEXLIST="indexList";
    //首页大图
    public static final String CHANNEL_INDEXPIC="indexPic";
    

	//如果不绑定师傅，返还200，否则返还300
    public static final int AWARD_WITH_MASTER_ACTIVITY =550;
    public static final int AWARD_WITH_MASTER =300;
    public static final int AWARD_NO_MASTER =200;

	//邀请徒弟成功，师父奖励400金币
    public static final int AWARD_INVITE = 400;
	//签到奖励10金币
    public static final int AWARD_SIGN = 10;

	//西瓜满月酒收第一个徒弟奖励5元
    public static final int AWARD_FIRST_DISCPLE = 500;

    //国庆活动
    //第一个徒弟
    public static final int AWARD_NATIONDAY_DISCPLE_1 = 660;
    public static final int AWARD_NATIONDAY_DISCPLE_5 = 1000;
    public static final int AWARD_NATIONDAY_DISCPLE_10 = 2000;
    
    
    public static final String BUNDLEID_XIGUAMEI = "com.jrutech.XiGuaMei";
    public static final String BUNDLEID_XIGUAMEI_LANDLORD_APPSTORE = "com.chaoluo.XiGuaMeiMemo";
    public static final String BUNDLEID_XIGUAMEI_LANDLORD_ENTERPRISE = "com.anjia.Memo";
    
    public static final int CREDIT_LOCATION = 70;
//    public static final int CREDIT_AWARD = 50;
    public static final int CREDIT_SHARE = 85;
    //前三个任务
//    public static final int CREDIT_FAST = 10;
    //前5个徒弟
    public static final int CREDIT_DISCIPLE = 40;
    
    
    //自助任务申请充值状态
    public static final String CHARGE_STATUS_INIT  = "INIT";
    public static final String CHARGE_STATUS_RECORD  = "RECORD";
    
    //自助任务申请充值状态
    public static final String AD_RECORD_OPERATOR_CHARGE  = "recharge";
    public static final String AD_RECORD_OPERATOR_SPEND  = "spend";

}
