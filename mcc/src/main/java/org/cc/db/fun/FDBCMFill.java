/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.PreparedStatement;
import java.util.List;
import org.cc.ICCFunction;
import org.cc.ICCParam;

/**
 *
 * @author William
 */
public class FDBCMFill implements ICCFunction<Object, Object[]> {
  
    public Object exec(PreparedStatement ps, List<ICCParam> params) throws Exception {
        if (params != null && params.size() > 0) {
            int idx = 1;
            for (ICCParam p : params) {
                p.type().setPS(ps, idx++, p.value());
            }
        }
        return null;
    }

    @Override
    public Object exec(Object ... param) throws Exception {
        PreparedStatement ps = (PreparedStatement)param[0];
        List<ICCParam> p =  ( List<ICCParam>)param[1];
        return exec(ps,p);
    }

}
