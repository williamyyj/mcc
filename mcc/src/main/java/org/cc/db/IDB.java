/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.db;

import java.sql.Connection;
import org.cc.ICCDC;

/**
 *
 * @author William
 */
public interface IDB extends ICCDC {
    
    public String database();

    public String schema();

    public String catalog();

    public Connection connection() throws Exception;
    
}
