package org.cc;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.cc.json.JOObject;
import org.cc.parser.CCJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA. User: william Date: 2013/7/30 Time: 上午 11:06 To
 * change this template use File | Settings | File Templates.
 */
public class CCCache {

    private static Logger log = LoggerFactory.getLogger(CCCache.class);

    private static ConcurrentHashMap<String, CCCacheItem> cache;


    public static ConcurrentHashMap cache() {
        if (cache == null) {
            cache = new ConcurrentHashMap<>();
        }
        return cache;
    }
    
    public static ICCObject load(String base, String id, String suffix){
        id = id.replace(".", "/");
        return load(new File(base,id+"." + suffix), "UTF-8");
    }
    
    public static ICCObject load(String base, String fid) {
         return load(new File(base,fid), "UTF-8");
    }

    @SuppressWarnings("unchecked")
    public static ICCObject load(File f, String enc) {
        if (f == null) {
            return null;
        }
        try {
            JOObject ret = null;
            String id = f.getCanonicalPath();
            CCCacheItem<ICCObject> item =(CCCacheItem<ICCObject>) cache().get(id);
            if (item != null && item.lastModified >= f.lastModified()) {
                log.debug("using cache " + item.id + " lastload : " + item.lastModified);
                return item.value;
            } else if (item != null) {
                return reload(f, enc);
            } else if (item == null && f.exists()) {
                return reload(f, enc);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static synchronized ICCObject reload(File f, String enc) {
        CCCacheItem<ICCObject> item = new CCCacheItem<>();
        try {
            item.id = f.getCanonicalPath();
            item.value = new CCJsonParser(f, enc).parser_obj();
            item.lastModified = System.currentTimeMillis();
            cache().put(item.id, item);
            return item.value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class CCCacheItem<E> {
        String id;
        long lastModified;
        E value;
    }

}
