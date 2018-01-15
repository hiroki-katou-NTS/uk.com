package nts.uk.ctx.at.record.dom.adapter.employment;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SyEmploymentAdapter {

	Optional<SyEmploymentImport> findByEmployeeId (String companyId, String employeeId, GeneralDate baseDate);
}
