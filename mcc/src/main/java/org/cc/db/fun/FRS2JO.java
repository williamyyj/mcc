/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import org.cc.ICCFunction;
import org.cc.ICCObject;
import org.cc.json.JOObject;

/**
 *
 * @author William
 */
public class FRS2JO implements ICCFunction<ICCObject, ResultSet> {

    @Override
    public ICCObject exec(ResultSet rs) throws Exception {
        JOObject jo = new JOObject();
        ResultSetMetaData rsmd = rs.getMetaData();
        int len = rsmd.getColumnCount();
        for (int i = 0; i < len; i++) {
            int idx = i + 1;
            String name = rsmd.getColumnName(idx);
            int dt = rsmd.getColumnType(idx);

            Object value = get_value(rs, name, dt);
            jo.set(name, value);
        }
        return jo;
    }
    
      protected Object get_value(ResultSet rs, String name, int dt) throws SQLException {
        //System.out.println("===== dt : " + dt + " name : " + name);
        switch (dt) {
            case Types.CHAR:
            case Types.LONGVARCHAR:
            case Types.VARCHAR:
                String ret = rs.getString(name);
                return (ret != null) ? ret.trim() : ret;
            case Types.CLOB:
                return rs.getString(name);
            case Types.DATE:
                return rs.getDate(name);
            case Types.TIME:
                return rs.getTime(name);
            case Types.TIMESTAMP:
                java.sql.Timestamp fld = rs.getTimestamp(name);
                return (fld != null) ? new Date(fld.getTime()) : null;
            default:
                return rs.getObject(name);
        }
    }

}
