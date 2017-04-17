package com.wangby.www.p1.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 数据管理工具
 * Created by 王炳炎zer on 2017/4/17.
 */

public class DataTool {

    private static SharedPreferences conf;
    /**
     * 键值对
     * 节点名称
     */
    public  static String UP_VERSION ="upversion";

    /**
     *用于保存配置文件
     * @param c 上下文
     * @param k 标识符 节点名称
     * @param v 键值 存储值
     */
    public static void saveBooleanConfig(Context c, String k, boolean v){
        if(conf == null){
            conf = c.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        conf.edit().putBoolean(k,v).commit();
    }

    /**
     * 读取相关键值
     * @param c 上下文
     * @param k 标识符 节点名称
     * @return 键值
     */
    public static boolean getBooleanConfig(Context c,String k){
        if(conf == null){
            conf = c.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return conf.getBoolean(k, false);
    }


    public static String getStringConfig(Context c,String k) {
        if(conf == null){
            conf = c.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return conf.getString(k, null);
    }
}
