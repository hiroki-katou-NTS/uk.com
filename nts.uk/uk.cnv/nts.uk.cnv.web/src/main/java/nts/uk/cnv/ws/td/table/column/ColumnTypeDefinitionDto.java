package nts.uk.cnv.ws.td.table.column;

import lombok.Value;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;

/**
 * 列の型定義
 */
@Value
public class ColumnTypeDefinitionDto {

	boolean isNullable;
	DataType dataType;
	int length;
	int scale;
	String defaultValue;
	String check;
}
