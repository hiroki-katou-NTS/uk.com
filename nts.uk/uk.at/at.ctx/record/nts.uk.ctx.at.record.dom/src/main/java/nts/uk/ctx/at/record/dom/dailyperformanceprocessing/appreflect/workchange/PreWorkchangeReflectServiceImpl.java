package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;

@Stateless
public class PreWorkchangeReflectServiceImpl implements PreWorkchangeReflectService{
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Override
	public boolean workchangeReflect(CommonReflectParameter workchangePara, boolean isPre) {
		try {
			for(int i = 0; workchangePara.getStartDate().daysTo(workchangePara.getEndDate()) - i >= 0; i++){
				GeneralDate loopDate = workchangePara.getStartDate().addDays(i);
				workchangePara.setBaseDate(loopDate);
				//予定勤種就時の反映
				commonService.reflectScheWorkTimeWorkType(workchangePara, isPre);
				//勤種・就時の反映
				ReflectParameter reflectPara = new ReflectParameter(workchangePara.getEmployeeId(),
						loopDate,
						workchangePara.getWorkTimeCode(),
						workchangePara.getWorkTypeCode());
				workTimeUpdate.updateWorkTimeType(reflectPara, false);
			}			
			return true;	
		}catch (Exception e) {
			return false;
		}
	}

}
