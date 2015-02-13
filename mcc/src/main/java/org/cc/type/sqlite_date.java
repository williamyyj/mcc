/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author William
 */
public class sqlite_date extends CCDateType {

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.TIMESTAMP);
        } else {
            Date d = check(value);
            SimpleDateFormat sdf = new SimpleDateFormat(fmt_datetime);
            ps.setObject(idx, sdf.format(d));
        }
    }
    
    public Date getRS(ResultSet rs, String name) throws SQLException {
        java.sql.Timestamp ts = rs.getTimestamp(name);
        System.out.println("===== sqlite_date  result :  " + ts);
        if (ts != null) {
            return new Date(ts.getTime());
        }
        return null;
    }

}
