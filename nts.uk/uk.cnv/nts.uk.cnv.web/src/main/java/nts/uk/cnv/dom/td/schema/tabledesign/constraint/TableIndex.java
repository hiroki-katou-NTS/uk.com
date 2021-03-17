package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

/**
 * インデックス定義
 */
@Value
public class TableIndex {
	String indexId;

	/** サフィックス */
	String suffix;

	/** 列IDリスト */
	List<String> columnIds;

	/** クラスタ化 */
	boolean isClustered;

	public String getCreateDdl(TableName tablename, List<ColumnDesign> columns) {
		val columnNames = columnIds.stream()
				.map(colId -> columns.stream()
						.filter(cd -> cd.getId().equals(colId))
						.findFirst()
						.get()
						.getName())
				.collect(Collectors.toList());
		return "CREATE INDEX "
				+ tablename.indexName(this.suffix) + " ON " + tablename
				+ " (" + String.join(",", columnNames) + ")";
	}
}
