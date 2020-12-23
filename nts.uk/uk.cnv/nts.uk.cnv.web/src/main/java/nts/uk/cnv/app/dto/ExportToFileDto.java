package nts.uk.cnv.app.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ExportToFileDto {
	String path;
	String type;
	boolean withComment;
	boolean oneFile;
	private String branch;
	private String date;

	public GeneralDate getDate() {
		return (this.date == null || this.date.isEmpty())
				? GeneralDate.today()
				: (this.date.contains("/"))
					? GeneralDate.fromString(this.date,"yyyy/MM/dd")
					: GeneralDate.fromString(this.date,"yyyy-MM-dd");
	}
}
