package test.empregulationhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EmploymentRegulationHistoryDto {

	private String historyId;
	
	private GeneralDate baseDate;
}
