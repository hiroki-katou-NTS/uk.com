package nts.uk.ctx.at.request.dom.setting.company.appreasonstandard;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppReasonStandardRepository {
	
	Optional<AppReasonStandard> findByAppType(String companyID, ApplicationType appType);
	
	Optional<AppReasonStandard> findByHolidayAppType(String companyID, HolidayAppType holidayAppType);
	
	Optional<AppReasonStandard> findByCD(ApplicationType appType, AppStandardReasonCode appStandardReasonCode);

	List<AppReasonStandard> findByCompanyId(String companyID);

	void saveReasonTypeItem(String companyId, int appType, Integer holidayAppType, ReasonTypeItem reasonItem);

	void deleteReasonTypeItem(String companyId, int appType, Integer holidayAppType, int reasonCode);
}
