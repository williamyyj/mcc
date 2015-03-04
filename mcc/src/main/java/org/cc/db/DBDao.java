package org.cc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.cc.ICCConnection;
import org.cc.ICCDao;
import org.cc.ICCObject;
import org.cc.ICCParam;
import org.cc.db.fun.DBFFill;
import org.cc.db.fun.DBFLoad;
import org.cc.db.fun.DBFRSParams;

public class DBDao implements ICCDao<Connection, ICCObject> {

    protected ICCConnection<Connection> ccconn;
    protected boolean isReference = false;
    protected ICCDBF<Object, PreparedStatement> dbf_fill;
    protected ICCDBF<ICCObject, ResultSet> dbf_load;
    protected ICCDBF<List<ICCParam>, ResultSet> dbf_rs_params;
    public DBDao(ICCConnection ccconn) {
        this.ccconn = ccconn;
        this.isReference = true;
        dbf_fill = new DBFFill();
        dbf_load = new DBFLoad();
        dbf_rs_params = new DBFRSParams();
    }

    @Override
    public ICCObject row(ICCObject jq) throws Exception {
        Connection conn = ccconn.connection();
        // DBCmd.parser_command(ccconn, null, jq)
        return null;
    }

    @Override
    public List<ICCObject> rows(ICCObject jq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object fun(ICCObject jq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int execute(ICCObject jq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] batch(ICCObject jq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void release(PreparedStatement ps) throws Exception {
        if (ps != null) {
            ps.close();
        }
    }

    private void release(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }

    public ICCObject row(String sql, Object... params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = ccconn.connection().prepareStatement(sql);
            dbf_fill.exec(ps, params);
            rs = ps.executeQuery();
            List<ICCParam> rs_params = dbf_rs_params.exec(rs, ccconn.types());
            return (rs != null && rs.next()) ? dbf_load.exec(rs,rs_params) : null;
        } finally {
            release(ps);
            release(rs);
        }
    }

    public List<ICCObject> rows(String sql, Object... params) throws Exception {
        List<ICCObject> ret = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = ccconn.connection().prepareStatement(sql);
            dbf_fill.exec(ps, params);
            rs = ps.executeQuery();
             List<ICCParam> rs_params = dbf_rs_params.exec(rs, ccconn.types());
            while (rs.next()) {
                ret.add(dbf_load.exec(rs,rs_params));
            }
            return ret;
        } finally {
            release(ps);
            release(rs);
        }
    }

}
