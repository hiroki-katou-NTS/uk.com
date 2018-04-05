package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.UpdateScheCommonAppRelect;
@Stateless
public class WorkChangeReflectServiceScheImpl implements WorkChangeReflectServiceSche{
	@Inject
	private UpdateScheCommonAppRelect updateSche;
	@Override
	public boolean reflectWorkChange(CommonReflectParamSche param) {
		try {
			//勤種・就時の反映
			updateSche.updateScheWorkTimeType(param.getEmployeeId(), param.getDatePara(), param.getWorktypeCode(), param.getWorkTimeCode());
			return true;
		} catch(Exception ex) {
			return false;	
		}
		
	}

}
