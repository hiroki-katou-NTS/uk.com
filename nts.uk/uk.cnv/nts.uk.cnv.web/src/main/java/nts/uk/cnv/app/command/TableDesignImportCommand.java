package nts.uk.cnv.app.command;

import lombok.Value;

@Value
public class TableDesignImportCommand {

	private String createTableSql;
	private String createIndexSql;
	private String type;
}
