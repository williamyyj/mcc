/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.cc.ICCInit;
import org.cc.ICCType;


/**
 *
 * @author william
 */
public class CCTypes {

    private Map<Object, ICCType<?>> types;
    protected ICCType<Object> var_type = new CCVarType();
    protected ICCType<Boolean> bool_type = new CCBoolType();
    protected ICCType<Integer> int_type = new CCIntType();
    protected ICCType<Long> long_type = new CCLongType();
    protected ICCType<Double> double_type = new CCDoubleType();
    protected CCDateType date_type = new CCDateType();
    protected ICCType<String> string_type = new CCStringType();
    protected ICCType<byte[]> blob_type = new CCBlobType();
    protected ICCType<String> clob_type = new CCClobType();

    public CCTypes() {
        this(null);
    }

    public CCTypes(String database) {
        types = new HashMap<>();
        init_commons();
        try {
            if (database != null) {
                ICCInit init = (ICCInit) Class.forName("org.cc2.type." + database + "_init").newInstance();
                init.__init__(types);
            }
        } catch (Exception ex) {
            System.out.println("Can't find org.cc2.type." + database + "_init");
        }

    }

    private void init_commons() {
        put(ICCType.dt_int, int_type);
        put(ICCType.dt_long, long_type);
        put(ICCType.dt_double, double_type);
        put(ICCType.dt_string, string_type);
        put(ICCType.dt_date, date_type);
        // jdbc
        put(Types.INTEGER, int_type);
        put(Types.DECIMAL, long_type);
        put(Types.BIGINT, long_type);
        put(Types.DOUBLE, double_type);
        put(Types.NUMERIC, long_type);
        put(Types.VARCHAR, string_type);
        put(Types.CHAR, string_type);
    }

    public ICCType<?> type(Object dt) {
        ICCType<?> type = types.get(dt);
        return (type != null) ? type : var_type;
    }

    public ICCType<?> type(int dt) {
        ICCType<?> type = types.get(dt);
        return (type != null) ? type : new CCVarType(dt);
    }

    public ICCType<?> type(int dt, String dt_name) {
        String name = dt_name.toLowerCase();
        if (name.startsWith("varchar") || name.startsWith("nvarchar")) {
            name = "string";
        }

        ICCType<?> type = types.get(name);
        if (type != null) {
            return type;
        } else {
            type = types.get(dt);
        }
        return (type != null) ? type : var_type;
    }

    public ICCType<?> var_type() {
        return var_type;
    }

    public ICCType<?> put(Object k, ICCType<?> type) {
        return types.put(k, type);
    }

}
