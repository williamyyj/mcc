/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cc.ICCObject;
import org.cc.ICCParam;
import org.cc.ICCType;
import org.cc.db.ICCDBF;
import org.cc.json.JOObject;
import org.cc.type.CCParam;
import org.cc.type.CCTypes;

/**
 * @author William
 */
public class DBFLoad implements ICCDBF<ICCObject, ResultSet> {

    private CCTypes types;

    @Override
    public ICCObject exec(ResultSet rs, Object... args) throws Exception {
        List<ICCParam> params = (List<ICCParam>) args[0];
        JOObject row = new JOObject();
        if (params != null) {
            for (ICCParam p : params) {
                row.set(p.name(), p.type().getRS(rs, p.name()));
            }
        }
        return row;
    }

}
