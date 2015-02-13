package org.cc.parser;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import org.cc.ICCList;
import org.cc.ICCObject;
import org.cc.json.JOList;
import org.cc.json.JOObject;

public class CCJsonParser extends CCBuffer {

    //<$xxxx> (:. )
    // <$render x model $>        
    private static final long serialVersionUID = 8384119638487566762L;
    public final static String tk_op_start = "<$";
    public final static String tk_op_end = "$>";
    public final static char tk_eval_start = '(';
    public final static char tk_expr_end = ')';
    public final static String tk_tmpl_start = "<<";
    public final static String tk_tmpl_end = ">>";
    protected int tabId = 0;

    public CCJsonParser(File f, String enc) {
        super(f, enc);
    }

    public CCJsonParser(String text) {
        super(text);
    }

    public ICCObject parser_obj() {
        tabId = 0;
        next();
        tk_csp();
        try {
            return cc_obj();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ICCList parser_list() {
        tabId = 0;
        next();
        tk_csp();
        try {
            return cc_list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    protected ICCObject cc_obj() throws Exception {
        if (!tk_m('{')) {
            throw syntax_error("XOObject exception '{' ");
        }
        JOObject jo = new JOObject();
        for (;;) {
            tk_csp();
            if (tk_m('}')) {
                return jo;
            }
            String key = cc_next(idPattern).toString();
            tk_csp();
            if (!tk_m(':')) {
                throw syntax_error("XOObject expected ':' ");
            }
            tk_csp();
            jo.put(key, cc_next(valuePattern));
            tk_csp();
            if (tk_m('}')) {
                return jo;
            }
            if (!tk_m(',')) {
                throw syntax_error("XOObject expected ( ',' | '}' ) ");
            }
        }
    }

    protected ICCList cc_list() throws Exception {
        if (!tk_m('[')) {
            throw syntax_error("ICCList expected '['");
        }
        JOList xa = new JOList();
        for (;;) {
            tk_csp();
            if (tk_m(']')) {
                return xa;
            }
            xa.add(cc_next(valuePattern));
            tk_csp();
            if (tk_m(']')) {
                return xa;
            }
            if (!tk_m(',')) {
                throw syntax_error("ICCList expected  (','|']') ");
            }
        }
    }

    public String cc_text() {
        start = ps;
        while (next() > 0 && !(m(tk_op_start) || m(tk_op_end) || m(tk_tmpl_end))) {
            if (ch == 9) {
                tabId++;
            }
            if (ch == 10 || ch == 13) {
                tabId = 0;
            }
        }
        return this.subString(start, ps);
        //if(m(tk_expr_start)) next(-1);
        //return new XOString(text);
    }

    public Object check_date(String s){
        if(s.startsWith("$date@")){
            if("$date@now".equals(s)){
                return new Date();
            }
            try {
                 if(s.length()==20){
                    return long_date_fmt.parse(s.substring(6));
                 } else if(s.length()==14){
                     return short_date_fmt.parse(s.substring(6));
                 }
            } catch (ParseException ex) {
                return s;
            }
        }
        return s;
    }
 
    public Object cc_next(String pattern) throws Exception {
        if (m('\'') || m('"')) {
            return check_date(cc_string(ch));
        } else if (m(tk_lstr_start)) {
            return cc_lstring();
        } else if (m('{')) {
            return cc_obj();
        } else if (m('[')) {
            return cc_list();
        } 
        return cc_value(cc_word(pattern));
    }

    public String cc_word(String pattern) {
        start = ps;
        // 合理換行是區原素 
        while (ch >= 32 && !in(0, pattern)) {
            next();
        }
        return subString(start, ps).trim();
    }

    public Object cc_value(String s) {
        if (s.equals("")) {
            return s;
        }
        if (s.equalsIgnoreCase("true")) {
            return true;
        }
        if (s.equalsIgnoreCase("false")) {
            return false;
        }
        if (s.equalsIgnoreCase("null")) {
            return null;
        }
        
        if(s.startsWith("$date@")){
            return check_date(s);
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
                //ignore.printStackTrace();
            }
        }
        return s;
    }

    public String cc_string(char quote) {
        return tk_string(quote);
    }

    public String cc_lstring() throws Exception {
        return tk_lstring();
    }
        
}