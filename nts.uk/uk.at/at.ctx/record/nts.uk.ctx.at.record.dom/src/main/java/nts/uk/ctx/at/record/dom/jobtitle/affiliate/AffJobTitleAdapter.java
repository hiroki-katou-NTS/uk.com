package nts.uk.ctx.at.record.dom.jobtitle.affiliate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AffJobTitleAdapter {

	Optional<AffJobTitleSidImport> findByEmployeeId(String employeeId, GeneralDate baseDate);
}
