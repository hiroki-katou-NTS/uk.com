package nts.uk.ctx.at.request.infra.repository.setting.company.appreasonstandard;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.HolidayAppType;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppReasonStandardRepository implements AppReasonStandardRepository {

	@Override
	public AppReasonStandard findByAppType(String companyID, ApplicationType appType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppReasonStandard findByHolidayAppType(String companyID, HolidayAppType holidayAppType) {
		// TODO Auto-generated method stub
		return null;
	}

}
