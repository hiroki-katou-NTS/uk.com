package nts.uk.cnv.app.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class GetUkColumnsParamDto {
	String category;
	String tableId;
	int recordNo;
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
