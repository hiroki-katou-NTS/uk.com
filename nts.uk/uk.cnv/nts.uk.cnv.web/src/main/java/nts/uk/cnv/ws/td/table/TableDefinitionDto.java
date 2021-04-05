package nts.uk.cnv.ws.td.table;

import java.util.List;

import lombok.Value;
import nts.uk.cnv.ws.td.table.column.ColumnDefinitionDto;

/**
 * テーブル定義
 */
@Value
public class TableDefinitionDto {

	TableInfoDto tableInfo;
	List<ColumnDefinitionDto> columns;
}
