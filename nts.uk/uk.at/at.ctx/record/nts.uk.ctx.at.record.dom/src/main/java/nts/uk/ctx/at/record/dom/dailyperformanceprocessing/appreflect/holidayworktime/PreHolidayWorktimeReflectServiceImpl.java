package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;

@Stateless
public class PreHolidayWorktimeReflectServiceImpl implements PreHolidayWorktimeReflectService{
	@Inject
	private HolidayWorkReflectProcess holidayWorkProcess;
	@Inject
	private WorkUpdateService workUpdate;
	@Inject
	private PreOvertimeReflectService overTimeService;
	@Inject
	private WorkUpdateService scheWork;
	@Inject
	private AdTimeAndAnyItemAdUpService timeAndAnyItemUpService;
	@Inject
	private CommonProcessCheckService commonService;
	@Override
	public boolean preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara, boolean isPre) {		
		try {
			List<IntegrationOfDaily> lstDaily = this.getIntegrationOfDaily(holidayWorkPara, isPre);
			commonService.updateDailyAfterReflect(lstDaily);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	@Override
	public IntegrationOfDaily createIntegrationOfDailyStart(String employeeId, GeneralDate baseDate
			, String workTimeCode, String workTypeCode, Integer startTime, Integer endTime, boolean isPre) {
		IntegrationOfDaily daily =overTimeService.calculateForAppReflect(employeeId, baseDate);
		if(daily == null) {
			return null;
		}
		if(isPre) {
			AttendanceTimeOfDailyPerformance attendanceTime = AttendanceTimeOfDailyPerformance.allZeroValue(employeeId, baseDate);
			daily.setAttendanceTimeOfDailyPerformance(Optional.of(attendanceTime));
			timeAndAnyItemUpService.addAndUpdate(daily);	
		}				
		return daily;
	}
	@Override
	public List<IntegrationOfDaily> getIntegrationOfDaily(HolidayWorktimePara holidayWorkPara, boolean isPre) {
		IntegrationOfDaily daily = this.createIntegrationOfDailyStart(holidayWorkPara.getEmployeeId(), 
				holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
				holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), holidayWorkPara.getHolidayWorkPara().getStartTime(), 
				holidayWorkPara.getHolidayWorkPara().getEndTime(), isPre);
		if(isPre) {
			// 予定勤種・就時の反映
			holidayWorkProcess.updateScheWorkTimeType(holidayWorkPara.getEmployeeId(),
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.isScheReflectFlg(), isPre,
					holidayWorkPara.getScheAndRecordSameChangeFlg(),
					daily);	
		}		
		//勤種・就時の反映
		ReflectParameter reflectInfo = new ReflectParameter(holidayWorkPara.getEmployeeId(), 
				holidayWorkPara.getBaseDate(), 
				holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
				holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(),
				false); 
		workUpdate.updateWorkTimeTypeHoliwork(reflectInfo, false, daily);
		TimeReflectPara timeData = new TimeReflectPara(holidayWorkPara.getEmployeeId(), 
				holidayWorkPara.getBaseDate(), 
				holidayWorkPara.getHolidayWorkPara().getStartTime(), 
				holidayWorkPara.getHolidayWorkPara().getEndTime(), 
				1, 
				true, 
				true);
		if(isPre) {
			//予定開始時刻の反映
			//予定終了時刻の反映			
			scheWork.updateScheStartEndTimeHoliday(timeData, daily);	
		}		
		//開始時刻と終了時刻の反映
		if(holidayWorkPara.getHolidayWorkPara().getStartTime() != null
				&& holidayWorkPara.getHolidayWorkPara().getEndTime() != null
				&& (isPre || (!isPre && holidayWorkPara.isHolidayWorkReflectFlg()))) {
			workUpdate.updateRecordStartEndTimeReflect(timeData, daily);
		}
		//休出時間の反映
		holidayWorkProcess.reflectWorkTimeFrame(holidayWorkPara, daily, isPre);
		//事前所定外深夜時間の反映
		workUpdate.updateTimeShiftNightHoliday(holidayWorkPara.getEmployeeId(),
				holidayWorkPara.getBaseDate(), 
				holidayWorkPara.getHolidayWorkPara().getNightTime(), 
				true, daily);
		List<IntegrationOfDaily> lstOutput =  commonService.lstIntegrationOfDaily(daily, holidayWorkPara.getEmployeeId(),
				holidayWorkPara.getBaseDate(), false);
		return lstOutput;
	}

}
