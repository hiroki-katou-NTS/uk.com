package nts.uk.ctx.hr.develop.app.empregulationhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DateHistoryItemDto {

	public String historyId;
	
	public GeneralDate startDate;
	
	public GeneralDate endDate;
	
}
