package nts.uk.cnv.app.command;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class UkTableDesignImportCommand {

	private String createTableSql;
	private String createIndexSql;
	private String commentSql;
	private String type;
	private String feature;
	private String date;

	public GeneralDate getDate() {
		return (this.date.isEmpty())
				? GeneralDate.today()
				: (this.date.contains("/"))
					? GeneralDate.fromString(this.date,"yyyy/MM/dd")
					: GeneralDate.fromString(this.date,"yyyy-MM-dd");
	}
}
