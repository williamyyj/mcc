/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.cc.CCCache;
import org.cc.ICCConnection;
import org.cc.ICCObject;
import org.cc.type.CCTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author william
 */

public abstract class DBC3P0 implements ICCConnection<Connection>{

    protected static Logger log = LoggerFactory.getLogger(DBC3P0.class);
    protected static Map mds;
    protected String base;
    protected Connection conn;
    protected ICCObject cfg;
    protected boolean is_reference = false;
    protected CCTypes types;

    public DBC3P0(String base) {
        this(base, null, null);
    }

    public DBC3P0(String base, String fid, String oid) {
        this.base = (base == null) ? System.getProperty("base") : base;
        ICCObject root = (fid != null) ? CCCache.load(base, fid, "json") : CCCache.load(base, "cfg.json");
        oid = (oid == null) ? "db" : oid;
        System.out.println(root);
        cfg = root.obj(oid);
        init_components();
    }

    public DBC3P0(String base, Connection conn) {
        this(base);
        if (conn != null) {
            is_reference = true;
            this.conn = conn;
        }
    }

    protected abstract void init_components();

    @Override
    public Connection connection() throws SQLException {
        if (conn == null) {
            String id = (cfg.has("id")) ? cfg.str("id") : "db";
            conn = getDataSource(id).getConnection();
        }
        return conn;
    }

    protected ComboPooledDataSource getDataSource(String id) {
        ComboPooledDataSource old = (ComboPooledDataSource) mds().get(id);
        if (old == null) {
            try {
                ComboPooledDataSource ds = new ComboPooledDataSource();
                ds.setUser(cfg.str("user"));
                ds.setPassword(cfg.str("password"));
                ds.setDriverClass(cfg.str("driver"));
                ds.setJdbcUrl(cfg.str("url"));
                ds.setMaxPoolSize(cfg.asInt("cp30.maxPoolSize", 50));
                ds.setMinPoolSize(cfg.asInt("cp30.minPoolSize", 5));
                ds.setMaxIdleTime(cfg.asInt("cp30.maxIdleTime", 3000));
                mds().put(id, ds);
                old = ds;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return old;
    }

    protected Map mds() {
        if (mds == null) {
            mds = new HashMap();
        }
        return mds;
    }

    protected void __release(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
    }

    protected void __release(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
            ps = null;
        }
    }

    protected void __release(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    @Override
    public void close() {
        try {
            if (!is_reference) {
                __release(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String base() {
        return base;
    }

    @Override
    public boolean isActived() {
        try {
            return (conn != null && !conn.isClosed());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public CCTypes types() {
        return this.types;
    }
    
    public ICCObject cfg() {
        return this.cfg;
    }

}
