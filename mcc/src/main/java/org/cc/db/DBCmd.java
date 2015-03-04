package org.cc.db;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cc.CCCache;
import org.cc.CCConst;
import org.cc.ICCConnection;
import org.cc.ICCObject;
import org.cc.json.JOList;
import org.cc.json.JOObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author William 整合DBJO ${base}/dp/xxxx.dao 統一使用
 *
 * ${ rem , xxxxxxxxxxxxxxxxxxxxxx}
 *
 * ${ op:fld_name:dt} 動態欄位 fld_name op map(fld_name) ${range:fld_name:dt}
 * fld_name beteen map(fld_name_1) and map(fld_name_2) ${like: fld_name} only
 * 字串欄位 會利用 IDB 的 base() 取到 base 位置
 *
 *
 */
public class DBCmd {

    private final static Logger log = LoggerFactory.getLogger(DBCmd.class);
    private final static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");
    private final static String dp_suffix = ".dao";
    private final static String dp_prefix = "dp/";
    public static ICCObject parser_command(ICCConnection<Connection> conn, String fid, String aid, ICCObject row) throws Exception {
        File f = new File(conn.base(), dp_prefix + fid + dp_suffix);
        ICCObject dao = CCCache.load(f, "UTF-8");      
        String cmd = DBCmdItem.get_command(dao, aid);
        //System.out.println(cmd);
        ICCObject model = new JOObject();
        model.set(CCConst.dp_params, new JOList());     
        Matcher m = p.matcher(cmd);
        StringBuffer sql = new StringBuffer();
        while (m.find()) {
            String item = m.group(1);
            m.appendReplacement(sql, ""); // 直接清空
            if (!item.startsWith("rem")) {
                if (dao.has(item)) {
                    String cmd_item = DBCmdItem.get_command(dao, item); //這部份不支援 recursive
                    sql.append(cmd_item);
                } else {
                    DBCmdItem.process_item(conn, sql, model, row, item);
                }
            }
        }
        m.appendTail(sql);
        model.set(CCConst.dp_sql, sql);
        return model;
    }

    public static ICCObject parser_command(ICCConnection<Connection> conn, String cmd, ICCObject row) throws Exception {
        ICCObject model = new JOObject();
        model.set(CCConst.dp_params, new JOList());     
        //System.out.println(cmd);
        Matcher m = p.matcher(cmd);
        StringBuffer sql = new StringBuffer();
        while (m.find()) {
            String item = m.group(1);
            m.appendReplacement(sql, ""); // 直接清空
            DBCmdItem.process_item(conn, sql, model, row, item);
        }
        m.appendTail(sql);
         model.set(CCConst.dp_sql, sql);
        return model;
    }

}
