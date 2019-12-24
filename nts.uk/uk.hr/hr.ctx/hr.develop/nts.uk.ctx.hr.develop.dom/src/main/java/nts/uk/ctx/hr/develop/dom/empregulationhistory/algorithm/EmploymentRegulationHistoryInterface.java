package nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface EmploymentRegulationHistoryInterface {
	
	// 基準日から就業規則の履歴IDの取得
	Optional<String> getHistoryIdByDate(String cId, GeneralDate baseDate);
}
