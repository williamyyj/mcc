package org.cc.parser;


import java.io.File;

/*
 *   在程式碼產生使用目前先使用MVEL
 *   (+ )目前先不支援
 */
public class CCELParser extends CCBufferBase {
    // 利用mevl 取值 
    public final static String el_prefix = "@{";
    public final static String fun_prfix = "@fun{";
    public final static String end_prfix = "@end{";
    public final static String each_prfix = "@each{";
    public final static String if_prfix = "@if{";
    public final static String elseif_prfix = "@elseif{";
    public final static String else_prfix = "@else{";
    
    public final static String suffix = "}";
 
    
    
 
    public CCELParser(File f, String enc) {
        super(f, enc);
    }

    public CCELParser(String text) {
        super(text);
    }

    public class XOToken {

        public int p_start;
        public int p_end;
        public int line;
        public int pos;
        public String text;
    }

    public void test() {
        long ts = System.nanoTime();
        tk_init();
        char ch = 0;
        while ((ch = next()) > 0) {
            //System.out.println(this);
        }
        long te = System.nanoTime();
        System.out.println("===== time : " + ((te - ts) / 1E9));
    }

    public static void main(String[] args) {
        CCELParser p = new CCELParser(new File("C:\\Users\\William\\Dropbox\\resources\\template\\mvel\\jsp\\fld_text.htm"), "UTF-8");
        p.test();
    }
}
