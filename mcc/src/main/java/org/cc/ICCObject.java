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

    public int asInt(String key) ; 
    
    public int asInt(String key, int dv);

    public long asLong(String key);
    
    public long asLong(String key, long dv);

    public double asDouble(String key) ; 
    
    public double asDouble(String key, double dv);
    
    public boolean asBool(String key) ; 
    
    public boolean asBool(String key, boolean dv) ; 
    
    public ICCList list(String key);
    
    public ICCObject obj(String key);
    
    public String toString(String indent);
    
    public String toString(String base, String indent);

}
