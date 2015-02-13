package org.cc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


/**
 * @author williamyyj
 */

public class CCIntType extends CCBaseType<Integer> {

    public String dt() {
        return dt_int;
    }

    public Integer check(Object o, Integer dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).intValue();
            } else if (o instanceof String) {
                 String str = ((String)o).trim();
                return str.length() > 0 ? Integer.parseInt(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Check " + this);
        }
        return dv;
    }

    public Integer getRS(ResultSet rs, String name) throws SQLException {
        return rs.getInt(name);
    }

    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.INTEGER);
        } else {
            ps.setInt(idx, check(value, 0));
        }
    }

    public Class<?> nativeClass() {
        return Integer.TYPE;
    }

    public int jdbc() {
        return Types.INTEGER;
    }
}
