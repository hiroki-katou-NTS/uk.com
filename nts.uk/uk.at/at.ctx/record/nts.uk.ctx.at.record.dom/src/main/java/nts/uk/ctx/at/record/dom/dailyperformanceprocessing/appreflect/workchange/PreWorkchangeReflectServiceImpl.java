package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;

@Stateless
public class PreWorkchangeReflectServiceImpl implements PreWorkchangeReflectService{
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private WorkInformationRepository workRepository;
	@Override
	public boolean workchangeReflect(CommonReflectParameter workchangePara, boolean isPre) {
		try {
			for(int i = 0; workchangePara.getStartDate().daysTo(workchangePara.getEndDate()) - i >= 0; i++){
				GeneralDate loopDate = workchangePara.getStartDate().addDays(i);
				workchangePara.setBaseDate(loopDate);
				WorkInfoOfDailyPerformance dailyInfor = workRepository.find(workchangePara.getEmployeeId(), loopDate).get();
				//予定勤種就時の反映
				dailyInfor = commonService.reflectScheWorkTimeWorkType(workchangePara, isPre, dailyInfor);
				//勤種・就時の反映
				ReflectParameter reflectPara = new ReflectParameter(workchangePara.getEmployeeId(),
						loopDate,
						workchangePara.getWorkTimeCode(),
						workchangePara.getWorkTypeCode(),
						true);
				
				dailyInfor = workTimeUpdate.updateWorkTimeType(reflectPara, false, dailyInfor);
				//日別実績の勤務情報  変更
				workRepository.updateByKeyFlush(dailyInfor);
			}			
			return true;	
		}catch (Exception e) {
			return false;
		}
	}

}
