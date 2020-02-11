package test.empregulationhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class DateHistoryItemDto {

	private String historyId; 
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
}
