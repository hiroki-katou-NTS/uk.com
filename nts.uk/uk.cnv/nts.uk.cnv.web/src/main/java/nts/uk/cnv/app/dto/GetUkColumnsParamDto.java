package nts.uk.cnv.app.dto;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

@Value
public class GetUkColumnsParamDto {
	String category;
	String tableId;
	int recordNo;
	private String feature;
	private String date;

	public GeneralDateTime getDateTime() {
		return GeneralDateTime.now();
	}
}
