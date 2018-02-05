package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;

@Stateless
public class JpaHolidayWorkInputRepository implements HolidayWorkInputRepository{

	@Override
	public List<HolidayWorkInput> getHolidayWorkInputByAttendanceID(String companyID, String appID, int attendanceId) {
		return null;
	}

}
