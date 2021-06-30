package nts.uk.ctx.exio.infra.repository.input.canonicalize.existing;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;

/**
 * AnyRecordToDelete用一時テーブルのレイアウト
 */
@RequiredArgsConstructor
public class LayoutAnyRecordToDelete {
	
	private final JdbcProxy jdbcProxy;
	private final ExecutionContext context;
	
	private static final String TABLE_NAME = "XIMTT_ANY_RECORD_TO_DELETE";
	
	public void createTable() {
		
		String tableName = TABLE_NAME + "_" + context.getCompanyId().replace("-", "");
		String sql = "create table " + tableName + "("
				+ " ITEM_NO decimal(5) not null,"
				+ " VALUE varchar(1000) null"
				+ ");";
		
		jdbcProxy.query(sql).execute();
	}
	
	public void insert(AnyRecordToDelete record) {
		
		String tableName = TABLE_NAME + "_" + context.getCompanyId().replace("-", "");
		
		record.getPrimaryKeys().forEach((itemNo, value) -> {
			
			String sql = "insert into " + tableName + " values ("
					+ itemNo
					+ ", " + value.asString()
					+ ")";
			
			jdbcProxy.query(sql).execute();
		});
	}
}
