package nts.uk.ctx.at.record.dom.confirmemployment;

import java.util.Optional;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
public interface RestrictConfirmEmploymentRepository {
	
	public Optional<RestrictConfirmEmployment> findByCompanyID(String companyID);
	
}
