package org.cc;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class CC {
    
    
    public static boolean _bool(Object o, boolean dv){
          if (o instanceof Number) {
            return (((Number) o).intValue()==1);
        } else if (o instanceof String) {
            try {
                String text = ((String) o).toLowerCase();
                return  ( "y".equals(text) || "yes".equals(text) || "true".equals(text) ) ; 
            } catch (Exception e) {
                return dv;
            }
        }
        return dv;
    }

    public static int _int(Object o, int dv) {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof String) {
            try {
                String text = (String) o;
                return Integer.parseInt(text.trim());
            } catch (Exception e) {
                return dv;
            }
        }
        return dv;
    }

    public static long _long(Object o, long dv) {
        if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof String) {
            try {
                String text = (String) o;
                return Long.parseLong(text.trim());
            } catch (Exception e) {
                return dv;
            }
        }
        return dv;
    }

    public static double _double(Object o, double dv) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        } else if (o instanceof String) {
            try {
                String text = (String) o;
                return Double.parseDouble(text.trim());
            } catch (Exception e) {
                return dv;
            }
        }
        return dv;
    }

    public static String json_date(Date o) {
        return new SimpleDateFormat("'$date@'yyyyMMddHHmmss").format(o);
    }

    public static String json(Object value) {
        if (value == null || value.equals("null")) {
            return "null";
        } else if (value instanceof Number) {
            return String.valueOf(value);
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof Date) {
            return json_date((Date) value);
        } else if (value instanceof String) {
            return json_str((String) value);
        } else {
            return value.toString();
        }
    }

    public static String json_str(String text) {
        if (text == null || text.length() == 0) {
            return "\"\"";
        }
        char[] buf = text.toCharArray();
        char b;
        String hhhh;
        int i;
        int len = text.length();
        StringBuilder sb = new StringBuilder(len + 4);
        sb.append('"');
        for (char c : buf) {
            b = c;
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0')
                        || (c >= '\u2000' && c < '\u2100')) {
                        hhhh = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(hhhh.substring(hhhh.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    public static String json(Object value, String base, String indent) {
        if (value == null || value.equals("null")) {
            return "null";
        } else if (value instanceof Number) {
            return String.valueOf(value);
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof Date) {
            return json_date((Date) value);
        } else if (value instanceof String) {
            return json_str((String) value);
        } else if (value instanceof ICCObject) {
            return ((ICCObject) value).toString(base, indent);
        } else if (value instanceof ICCList) {
            return ((ICCList) value).toString(base, indent);
        } else {
            return value.toString();
        }
    }

    public static void indent(StringBuilder sb, String indent, String value) {
        if (indent != null) {
            sb.append(indent).append(value);
        } else {
            sb.append(value);
        }
    }

}
