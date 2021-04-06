package nts.uk.cnv.dom.td.schema.tabledesign.constraint;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;

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

	public String tableContaint(TableName tableName, List<ColumnDesign> columns) {
		String pkContaint = this.primaryKey.getTableContaintDdl(tableName, columns);
		List<String> ukContaint = this.uniqueConstraints.stream()
				.map(uk -> uk.getTableContaintDdl(tableName, columns))
				.collect(Collectors.toList());
		return pkContaint
				+ (ukContaint.size() > 0
					? (",\r\n" + String.join(",\r\n", ukContaint ) + "\r\n")
					: "");
	}

	public String tableIndexes(TableName tableName, List<ColumnDesign> columns) {
		List<String> indexesConstraints = this.indexes.stream()
				.map(idx -> idx.getCreateDdl(tableName, columns))
				.collect(Collectors.toList());
		return String.join(",\r\n", indexesConstraints ) + "\r\n";
	}

	/**
	 * 主キーを除く一意キーとインデックスキーのCreate文を出力する
	 * @param tableDefineType
	 * @return
	 */
	public String getCreateDdl(TableName tableName, List<ColumnDesign> columns) {
		List<String> ukContaint = this.uniqueConstraints.stream()
				.map(uk -> uk.getCreateDdl(tableName, columns))
				.collect(Collectors.toList());
		List<String> indexesConstraints = this.indexes.stream()
				.map(idx -> idx.getCreateDdl(tableName, columns))
				.collect(Collectors.toList());
		return String.join(";\r\n", ukContaint)
				+ ((ukContaint.size() > 0) ? ";\r\n" : "")
				+ String.join(";\r\n", indexesConstraints )
				+ ((indexesConstraints.size() > 0) ? ";\r\n" : "");
	}
}
