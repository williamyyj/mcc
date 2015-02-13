/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

/**
 * @author William
 * @param <E>
 */
public interface ICCFilter<E> {
    public E exec(ICCObject cfg) throws Exception ; 
}
