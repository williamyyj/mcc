/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db.fun;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.function.BiConsumer;
import org.cc.ICCFunction;

/**
 *
 * @author William
 */
public class FDBPMDFill implements ICCFunction<Object, Object[]> {


    public Object exec(PreparedStatement ps, Object[] params) throws Exception {
        boolean pmd_support = true;
        ParameterMetaData pmd = null;
        if (params != null) {
            try {
                try {
                    pmd = ps.getParameterMetaData();
                    int stmtCount = pmd.getParameterCount();
                    int paramsCount = params.length;
                    if (stmtCount != paramsCount) {
                        throw new SQLException("Wrong number of parameters: expected " + stmtCount + ", was given " + paramsCount);
                    }
                } catch (Exception e) {
                    pmd_support = false;
                }

                // 設定參數
                System.out.println("===== check setting params ");
                for (int i = 0; i < params.length; i++) {
                    Object o = params[i];
                    if (o != null) {
                        proc_set_ps(ps, i + 1, o);
                    } else {
                        int sqlType = Types.VARCHAR;
                        if (!pmd_support) {
                            sqlType = pmd.getParameterType(i + 1);
                        }
                        ps.setNull(i + 1, sqlType);
                    }
                }
            } catch (Exception e) {
                pmd_support = false;
                e.printStackTrace();
            }
        }
        return null; 
    }

    private void proc_set_ps(PreparedStatement ps, int idx, Object o) throws SQLException {
        if (o instanceof Date) {
            Date d = (Date) o;
            java.sql.Timestamp ts = new java.sql.Timestamp(d.getTime());
            ps.setTimestamp(idx, ts);
        } else {
            ps.setObject(idx, o);
        }
    }

    @Override
    public Object exec(Object[] param) throws Exception {
        PreparedStatement ps = (PreparedStatement) param[0]; 
        Object[] p = (Object[]) param[1];
        return exec(ps,p);
    }
    
}
