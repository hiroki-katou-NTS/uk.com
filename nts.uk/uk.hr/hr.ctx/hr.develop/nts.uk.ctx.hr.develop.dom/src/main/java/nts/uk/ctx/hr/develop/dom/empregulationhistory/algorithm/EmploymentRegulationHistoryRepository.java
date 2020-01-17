package nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.EmploymentRegulationHistory;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.dto.RegulationHistoryDto;
import nts.uk.shr.com.history.DateHistoryItem;

public interface EmploymentRegulationHistoryRepository {

	Optional<RegulationHistoryDto> getLatestEmpRegulationHist(String cId);
	
	List<DateHistoryItem> getEmpRegulationHistList(String cId);
	
	String addEmpRegulationHist(String cId, GeneralDate startDate);
	
	void updateEmpRegulationHist(String cId, String historyId, GeneralDate startDate, GeneralDate endDate);
	
	void removeEmpRegulationHist(String cId, String historyId);
	
	Optional<EmploymentRegulationHistory> get(String cId);
}
