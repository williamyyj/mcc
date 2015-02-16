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
public interface ICCList {

    public int asInt(int index);

    public int asInt(int index, int dv);

    public long asLong(int index);

    public long asLong(int index, long dv);

    public double asDouble(int index);

    public double asDouble(int index, double dv);

    public boolean asBool(int index);

    public boolean asBool(int index, boolean dv);

    public ICCList list(int index);

    public ICCObject obj(int index);
    
    public Object opt(int index);

    public String toString(String indent);

    public String toString(String base, String indent);
    
    public int length();
    
    public Object set(int index, Object value);
    
    public boolean add(Object value);

}
