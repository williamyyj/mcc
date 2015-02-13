/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

/**
 *
 * @author William
 */
 
public interface ICCObject {

    public int asInt(Object key) ; 
    
    public int asInt(Object key, int dv);

    public long asLong(Object key);
    
    public long asLong(Object key, long dv);

    public double asDouble(Object key) ; 
    
    public double asDouble(Object key, double dv);

}
