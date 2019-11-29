package nts.uk.ctx.hr.develop.dom.empregulationhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

//dto for algorithm 最新履歴の履歴IDの取得 - getLatestEmpRegulationHist

@AllArgsConstructor
@Getter
public class RegulationHistoryDto {

	private String historyId;
	
	private GeneralDate baseDate;
}
