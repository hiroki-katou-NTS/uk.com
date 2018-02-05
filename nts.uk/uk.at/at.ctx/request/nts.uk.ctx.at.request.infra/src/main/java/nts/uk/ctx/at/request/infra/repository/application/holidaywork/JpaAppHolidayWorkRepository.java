package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
@Stateless
public class JpaAppHolidayWorkRepository implements AppHolidayWorkRepository{

	@Override
	public Optional<AppHolidayWork> getAppHolidayWork(String companyID, String appID) {
		// TODO Auto-generated method stub
		return null;
	}

}
