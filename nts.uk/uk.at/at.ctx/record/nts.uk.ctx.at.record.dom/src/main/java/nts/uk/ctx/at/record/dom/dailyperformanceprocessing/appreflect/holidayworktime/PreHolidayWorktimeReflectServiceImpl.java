package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;

@Stateless
public class PreHolidayWorktimeReflectServiceImpl implements PreHolidayWorktimeReflectService{
	@Inject
	private HolidayWorkReflectProcess holidayWorkProcess;
	@Inject
	private ScheWorkUpdateService workUpdate;
	@Inject
	private PreOvertimeReflectService overTimeService;
	@Inject
	private CalculateDailyRecordService calculate;
	@Inject
	private AttendanceTimeRepository attendanceTime;
	@Override
	public boolean preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara) {		
		try {
			// 予定勤種・就時の反映
			holidayWorkProcess.updateScheWorkTimeType(holidayWorkPara.getEmployeeId(),
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.isScheReflectFlg(), 
					holidayWorkPara.getScheAndRecordSameChangeFlg());
			//勤種・就時の反映
			ReflectParameter reflectInfo = new ReflectParameter(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode()); 
			workUpdate.updateWorkTimeType(reflectInfo, false);
			//事前休出時間の反映
			holidayWorkProcess.reflectWorkTimeFrame(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getMapWorkTimeFrame());
			//事前所定外深夜時間の反映
			workUpdate.updateTimeShiftNight(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getNightTime(), true);
			IntegrationOfDaily calculateData = calculate.calculate(overTimeService.calculateForAppReflect(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate()));
			attendanceTime.updateFlush(calculateData.getAttendanceTimeOfDailyPerformance().get());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
