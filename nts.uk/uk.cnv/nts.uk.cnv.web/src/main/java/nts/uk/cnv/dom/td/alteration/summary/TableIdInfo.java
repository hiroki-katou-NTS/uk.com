package nts.uk.cnv.dom.td.alteration.summary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * テーブル識別情報
 * @author ai_muto
 *
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TableIdInfo implements Comparable<TableIdInfo> {
	public String tableId;
	public String tableName;

	@Override
	public int compareTo(TableIdInfo o) {
		return this.tableName.compareTo(o.tableName);
	}

}