package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface StatusOfEmploymentAdapter {
	public Optional<StatusOfEmploymentResult> getStatusOfEmployment(String employeeId, GeneralDate referenceDate);
}
