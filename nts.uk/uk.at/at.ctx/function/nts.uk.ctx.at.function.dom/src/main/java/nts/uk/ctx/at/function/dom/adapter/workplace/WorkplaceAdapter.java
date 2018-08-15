package nts.uk.ctx.at.function.dom.adapter.workplace;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkplaceAdapter {
	Optional<WorkplaceImport> getWorlkplaceHistory(String employeeId, GeneralDate baseDate);
	
	List<WorkplaceIdName> findWkpByWkpId(String companyId, GeneralDate baseDate, List<String> wkpIds);
	
	List<WorkplaceImport> getWorlkplaceHistory(List<String> employeeId, GeneralDate baseDate);
	
	List<WorkplaceImport> getWorlkplaceHistoryByIDs(List<String> employeeIds);
}
