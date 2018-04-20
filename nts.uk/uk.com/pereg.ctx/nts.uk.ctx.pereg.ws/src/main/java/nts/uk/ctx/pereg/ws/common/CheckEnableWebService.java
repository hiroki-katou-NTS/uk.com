package nts.uk.ctx.pereg.ws.common;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.dom.remainingnumber.otherholiday.OtherHolidayInfoService;
import nts.uk.ctx.pereg.app.find.common.CheckEnableParam;
import nts.uk.ctx.pereg.dom.common.PredetemineTimeSettingRepo;
import nts.uk.ctx.pereg.dom.common.WorkTimeSettingRepo;

@Path("ctx/pereg/person/common")
@Produces("application/json")
public class CheckEnableWebService extends WebService {

	@Inject
	private WorkTimeSettingRepo workTimeSettingRepo;

	@Inject
	private PredetemineTimeSettingRepo predetemineTimeSettingRepo;

	@Inject
	private OtherHolidayInfoService otherHolidayInfoService;

	@POST
	@Path("checkStartEnd")
	public boolean checkStartDateAndEndDate(CheckEnableParam param) {
		if (param.getWorkTimeCode() != null) {
			return workTimeSettingRepo.isFlowWork(param.getWorkTimeCode());
		} else {
			return false;
		}
	}

	@POST
	@Path("checkMultiTime")
	public boolean checkMultiTime(CheckEnableParam param) {
		if (param.getWorkTimeCode() != null) {
			return predetemineTimeSettingRepo.isWorkingTwice(param.getWorkTimeCode());
		} else {
			return false;
		}
	}

	@Path("checkEnableRemainDays/{sid}")
	@POST
	public boolean checkEnableRemainDays(@PathParam(value = "sid") String sid) {
		return otherHolidayInfoService.checkEnableLeaveMan(sid);
	}

	@Path("checkEnableRemainLeft/{sid}")
	@POST
	public boolean checkEnableRemainLeft(@PathParam(value = "sid") String sid) {
		return otherHolidayInfoService.checkEnablePayout(sid);
	}
	
	@POST
	@Path("checkAllMutilTime")
	public boolean checkAllMultiTime(CheckEnableParam param) {
		boolean isWorkType = true;
		boolean isWorkTime = true;
		if (param.getWorkTimeCode() != null) {
			isWorkType =  predetemineTimeSettingRepo.isWorkingTwice(param.getWorkTimeCode());
		} else {
			isWorkType =  false;
		}
		
		if (param.getWorkTimeCode() != null) {
			isWorkTime =  workTimeSettingRepo.isFlowWork(param.getWorkTimeCode());
		} else {
			isWorkTime =  false;
		}
		
		return isWorkTime && isWorkType;
	}
}
