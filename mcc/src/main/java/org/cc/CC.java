package org.cc;

public class CC {

    public static int asInt(Object o, int dv) {
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

    public static long asLong(Object o, long dv) {
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

    public static double asDouble(Object o, double dv) {
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

}
