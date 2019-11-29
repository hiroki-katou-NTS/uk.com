package nts.uk.ctx.hr.develop.dom.empregulationhistory;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.dto.RegulationHistoryDto;
import nts.uk.shr.com.history.DateHistoryItem;

public interface EmploymentRegulationHistoryRepository {

	//最新履歴の履歴IDの取得
	Optional<RegulationHistoryDto> getLatestEmpRegulationHist(String cId);
	
	//就業規則の履歴の取得
	List<DateHistoryItem> getEmpRegulationHistList(String cId);
	
	//就業規則の履歴の追加
	String addEmpRegulationHist(String cId, GeneralDate startDate);
	
	//就業規則の履歴の更新
	void updateEmpRegulationHist(String cId, String historyId, GeneralDate startDate);
	
	//就業規則の履歴の削除
	void removeEmpRegulationHist(String cId, String historyId);
}
