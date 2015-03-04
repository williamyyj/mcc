package org.cc.db;

import java.util.List;
import org.cc.CCTest;
import org.cc.ICCObject;
import org.cc.json.JOObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author William
 */

public class DBTest {

    public DBTest() {
    }

    @Test
    public void test_oracle() throws Exception {
        String base = CCTest.base;
        System.out.println(base);
        DB db = new DB(base+"/prj/baphiq");
        try {
            DBDao dao = new DBDao(db);
            //Assert.assertEquals("check database", "mssql", db.database());
            //ICCObject jq = DBCmd.parser_command(db, "baphiq_gcis", "company_add", new JOObject());
            //List<ICCObject> rows = dao.rows("select * from store_t");
            ICCObject row = dao.row("select * from store_t");
            System.out.println(row);
        } finally {
            db.close();
        }
    }

}
