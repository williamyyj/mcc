/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import org.cc.type.CCTypes;

/**
 *
 * @author William
 */
public class DB extends DBC3P0 {

    public DB(String base) {
        super(base);
    }

    @Override
    protected void init_components() {
        types = new CCTypes(cfg.str("database"));
    }
    
}
