/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cc.CC;
import org.cc.ICCList;
import org.cc.ICCObject;

/**
 * @author William
 */

public class JOObject extends HashMap<String, Object> implements ICCObject {

    public JOObject(Map m) {
        super(m);
    }

    public JOObject() {
        super();
    }

    @Override
    public int asInt(String key, int dv) {
        return CC._int(get(key), dv);
    }

    @Override
    public long asLong(String key, long dv) {
        return CC._long(get(key), dv);
    }

    @Override
    public double asDouble(String key, double dv) {
        return CC._double(get(key), dv);
    }

    @Override
    public int asInt(String key) {
        return asInt(key, 0);
    }

    @Override
    public long asLong(String key) {
        return asLong(key, 0L);
    }

    @Override
    public double asDouble(String key) {
        return asDouble(key, 0.0);
    }

    @Override
    public boolean asBool(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asBool(String key, boolean dv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICCList list(String key) {
        Object o = get(key);
        return (o instanceof ICCList) ? (ICCList) o : null;
    }

    @Override
    public ICCObject obj(String key) {
        Object o = this.get(key);
        return (o instanceof ICCObject) ? (ICCObject) o : null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Set<Entry<String, Object>> list = this.entrySet();
        for (Entry<String, Object> e : list) {
            String k = e.getKey();
            Object v = e.getValue();
            sb.append('"').append(k).append('"').append(':').append(CC.json(v)).append(',');
        }
        if (size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append('}');
        return sb.toString();
    }

    public String toString(String indent) {
        return toString(null, indent);
    }

    public String toString(String base, String indent) {
        if (size() == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        String next = (base != null) ? base + indent : indent;
        sb.append("{\n");
        Set<Entry<String, Object>> list = this.entrySet();
        for (Entry<String, Object> e : list) {
            String k = e.getKey();
            Object v = e.getValue();
            CC.indent(sb, next, CC.json(k));
            sb.append(" : ").append(CC.json(v, next, indent));
            sb.append(",\n");
        };
        if (size() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append('\n');
        CC.indent(sb, base, "}");
        return sb.toString();
    }

    @Override
    public String str(String key) {
        Object o = get(key);
        return (o != null) ? o.toString().trim() : "";
    }

    @Override
    public boolean has(String key) {
        return this.containsKey(key);
    }

    @Override
    public Object opt(String key) {
        return get(key);
    }

    @Override
    public int length() {
        return size();
    }

    @Override
    public Object set(String key, Object value) {
        return this.put(key, value);
    }

}
