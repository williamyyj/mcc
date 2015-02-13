/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.Date;
import org.cc.json.JOList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author William
 */
public class CCCacheTest {

    @Test
    public void test_list_toString() {
        JOList cl = new JOList(new Object[]{1, true, 3, "4", 5, 6, 7, 8, 9, 10, new Date()});
        System.out.println(cl);
    }

    @Test
    public void test() {
        String base = CCTest.base;
        ICCObject jo = CCCache.load(base, "myprj/cfg.json");
        System.out.println(jo.toString("\t"));
    }

}
