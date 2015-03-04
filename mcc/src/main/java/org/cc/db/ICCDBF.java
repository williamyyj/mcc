/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

/**
 *
 * @author William
 * @param <RET>
 * @param <CTRL>
 */

public interface ICCDBF<RET,CTRL> {
    public RET exec(CTRL c, Object ... params) throws Exception ; 
}
