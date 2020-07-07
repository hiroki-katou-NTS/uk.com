package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.Optional;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppEmploymentSetRepository {
	
	public Optional<AppEmploymentSet> findByCompanyIDAndEmploymentCD(String companyID, String employmentCD);
	
}
