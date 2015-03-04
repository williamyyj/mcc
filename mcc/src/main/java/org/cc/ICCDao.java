/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.List;

/**
 *
 * @author William
 * @param <CONN>
 * @param <M>
 */
public interface ICCDao<CONN,M> {
    public M row(ICCObject jq) throws Exception ; 
    public List<M> rows(ICCObject jq) throws Exception ; 
    public Object fun(ICCObject jq) throws Exception ; 
    public int execute(ICCObject jq) throws Exception ; 
    public int[] batch(ICCObject jq) throws Exception ; 
}
