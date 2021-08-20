package nts.uk.ctx.exio.dom.input.workspace;

import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

public class TemporaryTable {
	
	private static final String PREFIX = "外部受入_";
	
	public static String createTableName(ExecutionContext context, String name) {
		return PREFIX + name + "_" + context.getCompanyId().replace("-", "");
	}
	
	public static void dropTable(JdbcProxy jdbcProxy, String tableName) {
		
		String sql = "drop table if exists " + tableName;

		jdbcProxy.query(sql).execute();
	}
}
