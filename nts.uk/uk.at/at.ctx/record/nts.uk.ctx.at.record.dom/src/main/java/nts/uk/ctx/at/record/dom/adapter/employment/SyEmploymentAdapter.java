package nts.uk.ctx.at.record.dom.adapter.employment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SyEmploymentAdapter {

	Optional<SyEmploymentImport> findByEmployeeId (String companyId, String employeeId, GeneralDate baseDate);
	
	Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes);
}
