/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import org.cc.ICCParam;
import org.cc.ICCType;
import org.cc.db.ICCDBF;
import org.cc.type.CCParam;
import org.cc.type.CCTypes;

/**
 * @author William
 */
public class DBFRSParams implements ICCDBF<List<ICCParam>, ResultSet> {

    @Override
    public List<ICCParam> exec(ResultSet rs, Object... params) throws Exception {
        List<ICCParam> ret = new ArrayList<>();
        CCTypes types = (CCTypes) params[0];
        ResultSetMetaData rsmd = rs.getMetaData();
        int len = rsmd.getColumnCount();
        for (int i = 1; i <= len; i++) {
            String name = rsmd.getColumnName(i);
            int jdbc = rsmd.getColumnType(i);
            String sqlType = rsmd.getColumnTypeName(i);
            ICCType<?> type = types.type(jdbc, sqlType);
            ret.add(new CCParam(type, name, null));
        }
        return ret;
    }

}
