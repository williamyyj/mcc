/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import junit.framework.Assert;
import org.cc.CCCache;
import org.cc.CCTest;
import org.cc.ICCObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author William
 */
public class DBTest {

    public DBTest() {
    }

    @Test
    public void test_oracle() throws Exception {
        String base = CCTest.base;
        DB db = new DB(base+"/prj/sonix");
        try {
            Assert.assertEquals("check database", "oracle", db.database());
            Assert.assertEquals("check database", "oracle", db.database());
            Assert.assertEquals("check database", "oracle", db.database());
        } finally {

        }
    }

}
