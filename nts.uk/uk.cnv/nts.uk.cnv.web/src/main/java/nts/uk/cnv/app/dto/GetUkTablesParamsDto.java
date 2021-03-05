package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUkTablesParamsDto {
	private String branch;
	private String date;

	public GeneralDateTime getDateTime() {
		return GeneralDateTime.now();
	}

}
