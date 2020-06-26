package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaDisplayReasonRepository implements DisplayReasonRepository {

	@Override
	public DisplayReason findByAppType(String companyID, ApplicationType appType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisplayReason findByHolidayAppType(String companyID, HolidayAppType holidayAppType) {
		// TODO Auto-generated method stub
		return null;
	}

}
