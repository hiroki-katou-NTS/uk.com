package nts.uk.ctx.at.record.dom.adapter.classification.affiliate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AffClassificationAdapter {
	
	Optional<AffClassificationSidImport> findByEmployeeId(String companyId, String employeeId, GeneralDate baseDate);

}
