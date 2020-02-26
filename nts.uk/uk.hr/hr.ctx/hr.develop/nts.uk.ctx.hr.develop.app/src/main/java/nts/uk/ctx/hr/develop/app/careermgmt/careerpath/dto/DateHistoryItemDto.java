package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class DateHistoryItemDto {

	public String historyId;
	
	public GeneralDate startDate;
	
	public GeneralDate endDate;
	
}
