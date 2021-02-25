package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface DisplayReasonRepository {
	
	Optional<DisplayReason> findByAppType(String companyID, ApplicationType appType);
	
	Optional<DisplayReason> findByHolidayAppType(String companyID, HolidayAppType holidayAppType);

	List<DisplayReason> findByCompanyId(String companyId);

	void saveHolidayAppReason(String companyId, List<DisplayReason> domains);
}
