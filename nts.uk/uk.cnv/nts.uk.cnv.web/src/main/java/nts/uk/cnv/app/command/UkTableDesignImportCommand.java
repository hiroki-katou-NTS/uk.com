package nts.uk.cnv.app.command;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class UkTableDesignImportCommand {

	private String createTableSql;
	private String createIndexSql;
	private String commentSql;
	private String type;
	private String branch;
	private GeneralDate date;
}
