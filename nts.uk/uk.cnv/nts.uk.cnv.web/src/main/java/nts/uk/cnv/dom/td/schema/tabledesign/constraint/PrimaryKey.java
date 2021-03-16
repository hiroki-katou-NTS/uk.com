package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

/**
 * 主キー定義
 */
@Value
public class PrimaryKey {
	String indexId;

	/** 列IDリスト */
	List<String> columnIds;

	/** クラスタ化 */
	boolean isClustered;

	public static PrimaryKey empty() {
		return new PrimaryKey(
				IdentifierUtil.randomUniqueId(),
				Collections.emptyList(),
				false);
	}

	public String getTableContaintDdl(TableName tableName, List<ColumnDesign> columns) {

		val columnNames = columnIds.stream()
			.map(colId -> columns.stream()
					.filter(cd -> cd.getId().equals(colId))
					.findFirst()
					.get()
					.getName())
			.collect(Collectors.toList());
		return "\t" + "CONSTRAINT "
				+ tableName.pkName() + " "
				+ "PRIMARY KEY"
				+ " (" + String.join(",", columnNames) + ")";
	}
}
