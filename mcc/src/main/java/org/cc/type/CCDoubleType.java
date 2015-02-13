/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class CCDoubleType extends CCBaseType<Double> {

    @Override
    public String dt() {
        return dt_double;
    }

    @Override
    public Double check(Object o, Double dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            } else if (o instanceof String) {
                String str = ((String)o).trim();
                return str.length() > 0 ? Double.parseDouble(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Check " + this);
        }
        return dv;
    }

    @Override
    public Double getRS(ResultSet rs, String name) throws SQLException {
      return rs.getDouble(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class<?> nativeClass() {
        return double.class;
    }
    
    @Override
    public int jdbc(){
        return Types.DOUBLE;
    }
    
}
