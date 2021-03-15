package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.Collections;
import java.util.List;

import lombok.Value;

/**
 * テーブル制約定義
 */
@Value
public class TableConstraints {

	PrimaryKey primaryKey;
	
	List<UniqueConstraint> uniqueConstraints;
	
	List<TableIndex> indexes;
	
	public static TableConstraints empty() {
		return new TableConstraints(
				PrimaryKey.empty(),
				Collections.emptyList(),
				Collections.emptyList());
	}
}
