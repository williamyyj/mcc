package org.cc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCBufferBase implements Serializable, CharSequence {

    protected Logger log = LoggerFactory.getLogger(CCBufferBase.class);
    protected char[] data;
    protected String enc = "UTF-8";
    protected int ps = 0;
    protected int start = 0;
    protected int line = 1;
    protected int pos = 0;
    protected char ch = 0;
    protected File src;

    public CCBufferBase(char[] data) {
        this.data = data;
    }

    public CCBufferBase(String text) {
        data = text.toCharArray();
    }

    public CCBufferBase(File f, String enc) {
        try {
            this.src = f;
            loadFromFile(f, enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String fName) {
        try {
            loadFromFile(new File(fName), enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(File f, String enc) throws Exception {
        data = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), enc));
        char[] tmp = new char[8192];
        int num = 0;
        try {
            while ((num = br.read(tmp)) > 0) {
                if (data == null) {
                    data = new char[num];
                    System.arraycopy(tmp, 0, data, 0, num);
                } else {
                    char[] old = data;
                    data = new char[old.length + num];
                    System.arraycopy(old, 0, data, 0, old.length);
                    System.arraycopy(tmp, 0, data, old.length, num);
                }
            }
        } finally {
            br.close();
        }
    }

    public int length() {
        return data.length;
    }

    public char charAt(int index) {
        return data[index];
    }

    public CharSequence subSequence(int start, int end) {
        int size = end - start;
        char[] buf = new char[size];
        System.arraycopy(data, start, buf, 0, size);
        return new CCBufferBase(buf);
    }

    public String subString(int start, int end) {
        return new String(data, start, end - start);
    }

    public String subString(int start) {
        return new String(data, start, data.length - start);
    }

    protected void tk_init() {
        ps = -1;
        line = 1;
        pos = 0;
    }

    protected char next() {
		//m$,linux,osx
        // \r\n,\n,\r
        ps++;
        if (ps < data.length) {
            if (ch == 10 || ch == 13) {
                line++;
                pos = 0;
            }
            ch = data[ps];
            if ((ch == 13) && data[ps + 1] == 10) {
                ps++;
            }
            pos++;
        } else {
            ch = 0;
        }
        return ch;
    }

    @Override
    public String toString() {
        String fmt = "[%s,pos(%s,%s),%s]";
        if (ch == 9) {
            return String.format(fmt, ps, line, pos, "\\t");
        } else if (ch == 10 || ch == 13) {
            return String.format(fmt, ps, line, pos, "\\n");
        } else if (ch == 0) {
            return String.format(fmt, ps, line, pos, "$eof");
        } else if (ch == 32) {
            return String.format(fmt, ps, line, pos, "\\s");
        } else {
            return String.format(fmt, ps, line, pos, ch);
        }
    }

}
