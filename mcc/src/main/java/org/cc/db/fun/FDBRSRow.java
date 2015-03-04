/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cc.db.fun;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiFunction;
import org.cc.ICCFunction;
import org.cc.ICCObject;
import org.cc.ICCParam;
import org.cc.json.JOObject;



/**
 *
 * @author William
 */
public class FDBRSRow implements ICCFunction<ICCObject,Object[]> {


    public ICCObject exec(ResultSet rs, List<ICCParam> params) throws Exception {
        JOObject row = new JOObject();
        if(params!=null){
            for(ICCParam p : params){
                row.set(p.name(), p.type().getRS(rs, p.name()) );
            }
        }
        return row ; 
    }

    @Override
    public ICCObject exec(Object[] param) throws Exception {
        ResultSet rs = (ResultSet) param[0];
        List<ICCParam> p= (List<ICCParam>) param[1];
        return exec(rs,p);
    }
   
}
