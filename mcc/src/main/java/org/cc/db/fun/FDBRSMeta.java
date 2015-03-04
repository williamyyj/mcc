/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cc.ICCFunction;
import org.cc.ICCParam;
import org.cc.ICCType;
import org.cc.type.CCParam;
import org.cc.type.CCTypes;

/**
 *
 * @author William
 */
public class FDBRSMeta implements ICCFunction<List<ICCParam>, Object[]> {

    public List<ICCParam> exec(ResultSet rs, CCTypes types) throws Exception {
        List<ICCParam> ret = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int len = rsmd.getColumnCount();
            for (int i = 1; i <= len; i++) {
                String name = rsmd.getColumnName(i);
                int jdbc = rsmd.getColumnType(i);
                String sqlType = rsmd.getColumnTypeName(i);
                ICCType<?> type = types.type(jdbc, sqlType);
                ret.add(new CCParam(type, name, null));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<ICCParam> exec(Object[] param) throws Exception {
        ResultSet rs = (ResultSet) param[0];
        CCTypes types = (CCTypes) param[1];
        return exec(rs,types);
    }

}
