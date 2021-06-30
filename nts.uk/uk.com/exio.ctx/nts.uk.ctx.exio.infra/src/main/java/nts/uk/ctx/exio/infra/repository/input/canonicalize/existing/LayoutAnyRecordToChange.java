package nts.uk.ctx.exio.infra.repository.input.canonicalize.existing;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;

/**
 * AnyRecordToChange用一時テーブルのレイアウト
 */
@RequiredArgsConstructor
public class LayoutAnyRecordToChange {
	
	private final JdbcProxy jdbcProxy;
	private final ExecutionContext context;
	
	private static final String TABLE_NAME = "XIMTT_ANY_RECORD_TO_CHANGE";
	private static final int ATR_PRIMARY_KEYS = 1;
	private static final int ATR_CHANGES = 2;
	
	public void createTable() {
		
		String sql = "create table " + tableName() + "("
				+ " ID char(36) not null"
				+ " ITEM_NO decimal(5) not null,"
				+ " ATR decimal(1) not null,"
				+ " VALUE varchar(1000) null"
				+ ");";
		
		jdbcProxy.query(sql).execute();
	}
	
	public void insert(AnyRecordToChange record) {
		
		insert(tableName(), record.getPrimaryKeys(), ATR_PRIMARY_KEYS);
		insert(tableName(), record.getChanges(), ATR_CHANGES);
	}
	
	private void insert(String tableName, Map<Integer, StringifiedValue> map, int atr) {
		
		map.forEach((itemNo, value) -> {
			String sql = "insert into " + tableName + " values ("
					+ IdentifierUtil.randomUniqueId()
					+ itemNo
					+ ", " + atr
					+ ", " + value.asString()
					+ ")";
			
			jdbcProxy.query(sql).execute();
		});
	}
	
	private String tableName() {
		return TABLE_NAME + "_" + context.getCompanyId().replace("-", "");
	}
}
