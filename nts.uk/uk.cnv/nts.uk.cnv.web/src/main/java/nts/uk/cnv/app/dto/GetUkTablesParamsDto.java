package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUkTablesParamsDto {
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
