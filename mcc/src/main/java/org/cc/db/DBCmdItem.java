package org.cc.db;

import java.util.HashMap;
import java.util.Map;
import org.cc.CCConst;
import org.cc.ICCList;
import org.cc.ICCObject;
import org.cc.ICCType;
import org.cc.type.CCParam;


/**
 * @author William 動態SQL用
 */

public class DBCmdItem {

    private static Map<String, String> op;
       
    private static Map<String, String> op() {
        if (op == null) {
            op = new HashMap<String, String>();
            op.put("=", "=");
            op.put(">", ">");
            op.put(">=", ">=");
            op.put("<", "<");
            op.put("<=", "<=");
            op.put("$like", "like"); //   xxx%
            op.put("$all", "like");   //   %xxxx%
            op.put("$range", "");  //     fld  betten a and b 
        }
        return op;
    }

    public static String get_command(ICCObject meta, String id) {
        Object o = meta.opt(id);
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof ICCList) {
            ICCList arr = meta.list( id);
            StringBuilder sb = new StringBuilder();
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    sb.append(arr.opt(i));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static void process_item(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String item) throws Exception {
        String[] args = item.split(",");
        String name = args[0];
        if (op().containsKey(name)) {
            process_op_item(db, sb, model, row, args);
        } else {
            process_var_item(db, sb, model, row, args);
        }

    }

    private static void process_op_item(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        String name = args[0];
        if ("=".equals(name) || ">".equals(name) || ">=".equals(name)
            || "<".equals(name) || "<=".equals(name)) {
            process_op2(db, sb, model, row, args);
        } else if ("$like".equals(name)) {
            process_like(db, sb, model, row, args);
        } else if ("$all".equals(name)) {
            process_all(db, sb, model, row, args);
        } else if ("$range".equals(name)) {
            process_range(db, sb, model, row, args);
        }
    }

    @SuppressWarnings("unchecked")
    private static void process_var_item(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        //    ${field,dt}  |   ${field,dt,alias} 
        String name = args[0];
        String dt = args[1];
        String alias = (args.length > 2) ? args[2] : null;
        ICCType<?> type = db.types().type(dt);
        ICCList params = model.list(CCConst.dp_params);
        Object value = get_value(type, row, name, alias);
        params.add(new CCParam(type, name, value));
        sb.append("?");
    }

    private static Object get_value(ICCType<?> type, ICCObject row, String name, String alias) {
        Object value = type.check(row.opt(name));
        if (value == null && alias != null) {
            value = type.check(row.opt(alias));
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private static void process_op2(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        String op_name = args[0];
        String name = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        ICCType<?> type = db.types().type(dt);
        ICCList params = model.list( CCConst.dp_params);
        Object value = get_value(type, row, name, alias);
        params.add(new CCParam(type, name, value));
        sb.append(" and").append(name).append(' ').append(op_name).append(" ?");
    }

    @SuppressWarnings("unchecked")
    private static void process_like(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        String op_name = args[0];
        String name = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        ICCType<?> type = db.types().type(dt);
        ICCList params = model.list(CCConst.dp_params);
        Object value = get_value(type, row, name, alias) + "%";
        params.add(new CCParam(type, name, value));
        sb.append(" and").append(name).append(' ').append(" like ").append(" ?");
    }

    @SuppressWarnings("unchecked")
    private static void process_all(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        String op_name = args[0];
        String name = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        ICCType<?> type = db.types().type(dt);
        ICCList params = model.list(CCConst.dp_params);
        Object value = "%" + get_value(type, row, name, alias) + "%";
        params.add(new CCParam(type, name, value));
        sb.append(" and").append(name).append(' ').append(" like ").append(" ?");
    }

    @SuppressWarnings("unchecked")
    private static void process_range(IDB db, StringBuffer sb, ICCObject model, ICCObject row, String[] args) {
        String op_name = args[0];
        String name = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        ICCType<?> type = db.types().type(dt);
        ICCList params = model.list( CCConst.dp_params);
        Object value1 = get_value(type, row, name + "_1", alias + "_1");
        Object value2 = get_value(type, row, name + "_2", alias + "_2");
        params.add(new CCParam(type, name + "_1", value1));
        params.add(new CCParam(type, name + "_2", value2));
        sb.append(" and").append(name).append(" beteen ? and ?");
    }

}
