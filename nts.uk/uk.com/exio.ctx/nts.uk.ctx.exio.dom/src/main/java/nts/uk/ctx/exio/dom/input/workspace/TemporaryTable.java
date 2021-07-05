package nts.uk.ctx.exio.dom.input.workspace;

import nts.arc.layer.infra.data.jdbc.JdbcProxy;

public class TemporaryTable {
	
	public static final String PREFIX = "外部受入_";
	
	public static void dropTable(JdbcProxy jdbcProxy, String tableName) {
		
		String sql = "drop table if exists " + tableName;

		jdbcProxy.query(sql).execute();
	}
}
