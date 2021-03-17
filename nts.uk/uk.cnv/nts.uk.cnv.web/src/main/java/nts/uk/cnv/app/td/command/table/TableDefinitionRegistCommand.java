package nts.uk.cnv.app.td.command.table;

import lombok.Value;
import nts.uk.cnv.ws.table.TableDefinitionDto;

/**
 * テーブル定義を変更する
 * @param tableDefinition
 */
@Value
public class TableDefinitionRegistCommand {
	private String featureId;
	private String userName;
	private String comment;
	private TableDefinitionDto td;
}
