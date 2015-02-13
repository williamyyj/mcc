package org.cc.parser;

import java.io.*;
import java.text.SimpleDateFormat;

public class CCBuffer extends CCBufferBase {

    public static final char QUOT = '"';
    /**
     * The pattern *
     */
    public final static String idPattern = " :()[]{}\\\"'";
    public final static String valuePattern = ",)]}>";
    public final static String wordPattern = " ,)]}>";
    public final static String opPattern = " ,)]}$";
    public final static String tk_lstr_start = "$\"";  // $".....""..."
    protected SimpleDateFormat long_date_fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    protected SimpleDateFormat short_date_fmt = new SimpleDateFormat("yyyyMMdd");

    public CCBuffer(String text) {
        super(text);
        tk_init();
    }

    public CCBuffer(File f, String enc) {
        super(f, enc);
        tk_init();
    }

    public char next() {
		//m$,linux,osx
        // \r\n,\n,\r
        ps++;
        ch = (ps < data.length) ? data[ps] : 0;
        return ch;
    }

    protected char next(int offset) {
        ps += offset;
        if (ps < data.length) {
            if (ch == 10 || ch == 13) {
                line++;
                pos = 0;
            }
            ch = data[ps];
            if ((ch == 13) && data[ps + 1] == 10) {
                ps++;
            }
            pos += offset;
        } else {
            ch = 0;
        }
        return ch;
    }

    // ".........................""...........""..........."
    public String tk_lstring() {
        StringBuffer sb = new StringBuffer();
        if (tk_m(tk_lstr_start)) {
            for (;;) {
                if (ch == 0) {
                    break;
                }
                if (ch == QUOT) {
                    if (m(1, QUOT)) {
                        sb.append(QUOT);
                        next();
                    } else {
                        break;
                    }
                } else {
                    sb.append(ch);
                }
                next();
            }
            next();
        }
        return sb.toString();
    }

    public String tk_string(char quote) {
        StringBuffer sb = new StringBuffer();
        while (next() != quote) {
            switch (ch) {
                case 0:
                    break;
                case '\\':
                    next();
                    switch (ch) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(subString(ps + 1, ps + 5), 16));
                            ps += 4;
                            pos += 4;
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                            sb.append(ch);
                            break;
                    }
                    break;
                default:
                    sb.append(ch);
            }
        }
        if (!m(quote)) {
            syntax_error("String exception [',\'] ");
        }
        next();
        return sb.toString();
    }

    public boolean tk_m(char c) {
        if (m(c)) {
            next();
            return true;
        }
        return false;
    }

    public boolean tk_m(String str) {
        if (m(str)) {
            next(str.length());
            return true;
        }
        return false;
    }

    public boolean m(char c) {
        return (ch == c);
    }

    public boolean m(int idx, char c) {
        return ((ps + idx) < data.length && c == data[ps + idx]);
    }

    public boolean m(String text) {
        return m(ps, text);
    }

    public boolean mi(String text) {
        return mi(ps, text);
    }

    public boolean m(int idx, String text) {
        char[] buf = text.toCharArray();
        for (char c : buf) {
            if ((idx >= data.length || c != data[idx++])) {
                return false;
            }
        }
        return true;
    }

    public boolean mi(int idx, String text) {
        char[] buf = text.toCharArray();
        for (char c : buf) {
            if (idx >= data.length) {
                return false;
            }
            char a = Character.toLowerCase(c);
            char b = Character.toLowerCase(data[idx++]);
            if (a != b) {
                return false;
            }
        }
        return true;
    }

    public boolean in(int idx, String text) {
        return (text.indexOf(data[ps + idx]) >= 0);
    }

    @SuppressWarnings("empty-statement")
    public void tk_csp() {
        while (isWhiteSpace() || m("/*")) {
            if (isWhiteSpace()) {
                while (next() == 9 || ch == 10 || ch == 13 || ch == 32) {
                };
            } else if (m("/*")) {
                tk_comment();
            }
        }
    }

    public void tk_comment() {
        if (tk_m("/*")) {
            while (!m("*/") && ch != 0) {
                next();
            }
            if (ch != 0) {
                tk_m("*/");
            }
        }
    }

    public boolean isWhiteSpace() {
        return (ch == 9 || ch == 10 || ch == 13 || ch == 32);
    }

    public Exception syntax_error(String message) {
        String base = (src == null) ? this.toString() : src.getName() + "::::" + this.toString();
        log.debug(message + ", in  " + base);
        log.debug(new String(data,0,ps));
        return new Exception(message + ", in  " + base);
    }

    public String tk_item(String pattern) {
        start = ps;
        while (!in(0, pattern)) {
            next();
        }
        //if(pattern.indexOf(ch)<0) next(-1);
        String text = subString(start, ps).trim();
        return text;
    }

    public Object tk_value(String s) {
        if (s.equals("")) {
            return "";
        }
        if (s.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (s.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (s.equalsIgnoreCase("null")) {
            return null;
        }
        char b = s.charAt(0);
        if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
            if (b == '0' && s.length() > 2
                && (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {
                try {
                    return Integer.parseInt(s.substring(2), 16);
                } catch (Exception ignore) {
                }
            }
            try {
                if (s.indexOf('.') > -1 || s.indexOf('e') > -1
                    || s.indexOf('E') > -1) {
                    return Double.parseDouble(s);
                } else {
                    Long myLong = new Long(s);
                    if (myLong.longValue() == myLong.intValue()) {
                        return myLong.intValue();
                    } else {
                        return myLong.longValue();
                    }
                }
            } catch (Exception ignore) {
            }
        }
        return s.trim();
    }

}
