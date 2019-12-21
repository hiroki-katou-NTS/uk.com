package test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

//dto for algorithm 最新履歴の履歴IDの取得 - getLatestEmpRegulationHist

@AllArgsConstructor
@Getter
public class EmploymentRegulationHistoryDto {

	private String historyId;
	
	private GeneralDate baseDate;
}
