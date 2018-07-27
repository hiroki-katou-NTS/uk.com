package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.holidaywork;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.UpdateScheCommonAppRelect;

@Stateless
public class HolidayWorkReflectScheImpl implements HolidayWorkReflectSche{
	@Inject
	private UpdateScheCommonAppRelect scheCommon;
	@Override
	public boolean holidayWorkReflect(CommonReflectParamSche holidayWorkPara) {
		try {
			//勤種・就時の反映
			scheCommon.updateScheWorkTimeType(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getDatePara(), 
					holidayWorkPara.getWorktypeCode(),
					holidayWorkPara.getWorkTimeCode());
			
			return true;	
		}catch (Exception e) {
			return false;
		}
		
	}

}
