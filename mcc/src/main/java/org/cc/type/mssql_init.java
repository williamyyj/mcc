package org.cc.type;

import java.sql.Types;
import java.util.Map;
import org.cc.ICCInit;

/**
 * Created with IntelliJ IDEA. User: william Date: 2013/7/29 Time: 下午 5:31 To
 * change this template use File | Settings | File Templates.
 */
public class mssql_init implements ICCInit {

    @SuppressWarnings("unchecked")
    @Override
    public void __init__(Map self) throws Exception {
        self.put(Types.DATE, new mssql_date());
        self.put(Types.TIME, new mssql_date());
        self.put(Types.TIMESTAMP, new mssql_date());
    }

}
