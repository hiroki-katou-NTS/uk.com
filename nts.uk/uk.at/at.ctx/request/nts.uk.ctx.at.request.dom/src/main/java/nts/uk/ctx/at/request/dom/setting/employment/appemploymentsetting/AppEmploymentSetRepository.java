package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppEmploymentSetRepository {

    List<AppEmploymentSet> findByCompanyID(String companyID);

	Optional<AppEmploymentSet> findByCompanyIDAndEmploymentCD(String companyID, String employmentCD);

	void insert(AppEmploymentSet domain);

	void update(AppEmploymentSet domain);

	void delete(String companyId, String employmentCode);
	
}
