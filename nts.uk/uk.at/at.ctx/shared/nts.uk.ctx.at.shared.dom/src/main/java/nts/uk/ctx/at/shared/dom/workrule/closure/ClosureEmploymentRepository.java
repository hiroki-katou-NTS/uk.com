package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.Optional;

public interface ClosureEmploymentRepository {
	
	public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD);
	
}
