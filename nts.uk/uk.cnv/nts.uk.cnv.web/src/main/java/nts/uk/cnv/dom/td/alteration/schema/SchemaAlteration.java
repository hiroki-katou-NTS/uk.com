package nts.uk.cnv.dom.td.alteration.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.schema.TableIdentity;

/**
 * スキーマoruta
 */
@Value
public class SchemaAlteration {

	/** orutaID */
	String alterId;
	
	/** 日時 */
	GeneralDateTime datetime;
	
	/** 対象テーブル */
	TableIdentity targetTable;
	
	/** 操作 */
	SchemaAlterationOperation operation;
	
	/** 変更後のテーブル名 */
	Optional<String> newTableName;
	
	/**
	 * 適用する
	 * @param targetTables
	 * @return
	 */
	public List<TableIdentity> apply(List<TableIdentity> targetTables) {
		
		val result = new ArrayList<>(targetTables);
		
		switch (operation) {
		case CREATE_TABLE:
			result.add(targetTable);
			return result;
		case DROP_TABLE:
			result.remove(targetTable);
			return result;
		case RENAME_TABLE:
			int i = result.indexOf(targetTable);
			result.get(i).rename(newTableName.get());
			return result;
		default:
			throw new RuntimeException("unknown: " + operation);
		}
	}
}
