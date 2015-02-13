/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.json;

import com.google.common.base.Optional;
import java.util.HashMap;
import org.cc.CC;
import org.cc.ICCObject;

/**
 *
 * @author William
 */
public class JOObject extends HashMap<String,Object> implements ICCObject {

    @Override
    public int asInt(Object key, int dv) {
       return  CC.asInt(get(key), dv);
    }

    @Override
    public long asLong(Object key, long dv) {
       return CC.asLong(get(key),dv);
    }

    @Override
    public double asDouble(Object key, double dv) {
        return CC.asDouble(get(key), dv);
    }

    @Override
    public int asInt(Object key) {
        return asInt(key,0);
     }

    @Override
    public long asLong(Object key) {
        return asLong(key,0L);
    }

    @Override
    public double asDouble(Object key) {
        return asDouble(key,0.0);
    }

    

    
}
