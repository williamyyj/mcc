/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import org.cc.type.CCTypes;

/**
 *
 * @author William
 */
public interface ICCDC {

    public boolean isActived();
    
    public String base();

    public CCTypes types();
    
    public void close();

}
