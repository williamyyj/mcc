package org.cc.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import org.cc.CC;
import org.cc.ICCList;
import org.cc.ICCObject;

/**
 * @author William
 */
public class JOList extends ArrayList<Object> implements ICCList {

    public JOList(){
        super();
    }
    
    public JOList(Collection c) {
        super(c);
    }

    public JOList(Object array) {
        super();
        if (array != null && array.getClass().isArray()) {
            int length = Array.getLength(array);
            for (int i = 0; i < length; i += 1) {
                add(Array.get(array, i));
            }
        } else {
            throw new RuntimeException("JOList initial value should be a string or collection or array.");
        }
    }

    @Override
    public int asInt(int index) {
        return CC._int(index, 0);
    }

    @Override
    public int asInt(int index, int dv) {
        return CC._int(get(index), dv);
    }

    @Override
    public long asLong(int index) {
        return CC._long(index, 0L);
    }

    @Override
    public long asLong(int index, long dv) {
        return CC._long(get(index), dv);
    }

    @Override
    public double asDouble(int index) {
        return CC._double(index, 0.0);
    }

    @Override
    public double asDouble(int index, double dv) {
        return CC._double(get(index), dv);
    }

    @Override
    public boolean asBool(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asBool(int index, boolean dv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICCList list(int index) {
        Object o = this.get(index);
        return (o instanceof ICCList) ? (ICCList) o : null;
    }

    @Override
    public ICCObject obj(int index) {
        Object o = this.get(index);
        return (o instanceof ICCObject) ? (ICCObject) o : null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Object o : this) {
            sb.append(CC.json(o)).append(',');
        }
        if (size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public String toString(String indent) {
        return toString(null, indent);
    }

    @Override
    public String toString(String base, String indent) {
        if (size() == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        String next = (base == null) ? indent : base + indent;
        sb.append("[\n");
        for (Object o : this) {
            CC.indent(sb, next, CC.json(o, next, indent));
            sb.append(",\n");
        }
        if (size() > 0) {
            sb.setLength(sb.length() - 2);
        }
        sb.append('\n');
        CC.indent(sb, base, "]");
        return sb.toString();
    }

}
