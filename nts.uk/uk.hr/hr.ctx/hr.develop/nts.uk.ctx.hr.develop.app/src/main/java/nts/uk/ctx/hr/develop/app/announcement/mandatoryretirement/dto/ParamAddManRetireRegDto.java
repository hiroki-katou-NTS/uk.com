package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ParamAddManRetireRegDto {

	private String historyId;
	
	private String baseDate;

	public String getHistoryId() {
		return historyId;
	}

	public GeneralDate getBaseDate() {
		return GeneralDate.fromString(baseDate, "yyyy/MM/dd");
	}
	
}
