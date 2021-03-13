package nts.uk.cnv.dom.td.schema;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * テーブルの識別情報
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TableIdentity {
	
	/** テーブルID */
	@Getter
	String tableId;
	
	/** 物理名 */
	@Getter
	String name;
	
	public TableIdentity rename(String newName) {
		name = newName;
		return this;
	}
}
