package nts.uk.cnv.ws.table.column;

import lombok.Value;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

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
