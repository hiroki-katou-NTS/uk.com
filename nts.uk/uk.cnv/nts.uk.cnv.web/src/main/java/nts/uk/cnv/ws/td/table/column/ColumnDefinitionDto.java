package nts.uk.cnv.ws.td.table.column;

import lombok.Value;

/**
 * 列定義
 */
@Value
public class ColumnDefinitionDto {

	String id;
	String name;
	String nameJp;
	ColumnTypeDefinitionDto type;
	String comment;
}
