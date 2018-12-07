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
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

@Stateless
public class PreWorkchangeReflectServiceImpl implements PreWorkchangeReflectService{
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private WorkTypeIsClosedService workTypeRepo;
	@Override
	public boolean workchangeReflect(WorkChangeCommonReflectPara param, boolean isPre) {
		try {
			CommonReflectParameter workchangePara = param.getCommon();
			for(int i = 0; workchangePara.getStartDate().daysTo(workchangePara.getEndDate()) - i >= 0; i++){
				GeneralDate loopDate = workchangePara.getStartDate().addDays(i);
				workchangePara.setBaseDate(loopDate);
				WorkInfoOfDailyPerformance dailyInfor = workRepository.find(workchangePara.getEmployeeId(), loopDate).get();
				//1日休日の判断
				if(dailyInfor.getRecordInfo().getWorkTypeCode() != null
						&& param.getExcludeHolidayAtr() == 1
						&& workTypeRepo.checkHoliday(dailyInfor.getRecordInfo().getWorkTypeCode().v())) {
					continue;
				}
				
				//予定勤種就時の反映
				dailyInfor = commonService.reflectScheWorkTimeWorkType(workchangePara, isPre, dailyInfor);
				
				ReflectParameter reflectPara = new ReflectParameter(workchangePara.getEmployeeId(),
						loopDate,
						workchangePara.getWorkTimeCode(),
						workchangePara.getWorkTypeCode(),
						true);
				//勤種・就時の反映
				dailyInfor = workTimeUpdate.updateWorkTimeType(reflectPara, false, dailyInfor);
				//日別実績の勤務情報  変更
				workRepository.updateByKeyFlush(dailyInfor);
				commonService.calculateOfAppReflect(null, workchangePara.getEmployeeId(), loopDate);
			}			
			return true;	
		}catch (Exception e) {
			return false;
		}
	}

}
