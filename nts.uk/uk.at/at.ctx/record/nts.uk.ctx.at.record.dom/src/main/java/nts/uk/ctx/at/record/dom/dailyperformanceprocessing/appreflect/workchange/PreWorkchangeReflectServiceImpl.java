package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

@Stateless
public class PreWorkchangeReflectServiceImpl implements PreWorkchangeReflectService{
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private WorkTypeIsClosedService workTypeRepo;
	@Inject
	private PreOvertimeReflectService preOvertime;
	@Override
	public boolean workchangeReflect(WorkChangeCommonReflectPara param, boolean isPre) {
		try {
			List<IntegrationOfDaily> lstDailys = this.getByWorkChange(param, isPre);
			commonService.updateDailyAfterReflect(lstDailys);
			return true;	
		}catch (Exception e) {
			return false;
		}
	}
	@Override
	public List<IntegrationOfDaily> getByWorkChange(WorkChangeCommonReflectPara param, boolean isPre) {
		CommonReflectParameter workchangePara = param.getCommon();
		List<IntegrationOfDaily> lstOutput = new ArrayList<>();
		for(int i = 0; workchangePara.getStartDate().daysTo(workchangePara.getEndDate()) - i >= 0; i++){			
			GeneralDate loopDate = workchangePara.getStartDate().addDays(i);
			IntegrationOfDaily dailyInfor = preOvertime.calculateForAppReflect(workchangePara.getEmployeeId(), workchangePara.getBaseDate());
			workchangePara.setBaseDate(loopDate);
			//1日休日の判断
			WorkInformation recordInfo = dailyInfor.getWorkInformation().getRecordInfo();
			if(recordInfo.getWorkTypeCode() != null
					&& param.getExcludeHolidayAtr() == 1
					&& workTypeRepo.checkHoliday(recordInfo.getWorkTypeCode().v())) {
				continue;
			}
			
			//予定勤種就時の反映
			boolean isScheReflect = commonService.reflectScheWorkTimeWorkType(workchangePara, isPre, dailyInfor);
			TimeReflectPara timeReflect =  new TimeReflectPara(param.getCommon().getEmployeeId(),
					loopDate,
					param.getCommon().getStartTime(),
					param.getCommon().getEndTime(),
					1, true, true);
			//予定開始終了時刻の反映
			if(param.getCommon().getStartTime() != null 
					&& param.getCommon().getEndTime() != null
					&& isScheReflect) {
				workTimeUpdate.updateScheStartEndTime(timeReflect, dailyInfor);	
			}
			ReflectParameter reflectPara = new ReflectParameter(workchangePara.getEmployeeId(),
					loopDate,
					workchangePara.getWorkTimeCode(),
					workchangePara.getWorkTypeCode(),
					true);
			//勤種・就時の反映
			workTimeUpdate.updateWorkTimeType(reflectPara, false, dailyInfor);
			//開始終了時刻の反映
			if(param.getCommon().getStartTime() != null 
					&& param.getCommon().getEndTime() != null) {
				workTimeUpdate.updateRecordStartEndTimeReflect(timeReflect, dailyInfor);
			}
			//日別実績の勤務情報  変更
			List<IntegrationOfDaily> lstDaily = commonService.lstIntegrationOfDaily(dailyInfor, workchangePara.getEmployeeId(), loopDate, false);
			lstOutput.addAll(lstDaily);
		}
		return lstOutput;
	}

}
