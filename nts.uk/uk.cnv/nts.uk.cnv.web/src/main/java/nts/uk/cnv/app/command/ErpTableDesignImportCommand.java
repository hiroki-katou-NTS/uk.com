package nts.uk.cnv.app.command;

import lombok.Value;

@Value
public class ErpTableDesignImportCommand {

	private String createTableSql;
	private String createIndexSql;
	private String commentSql;
	private String type;
}
