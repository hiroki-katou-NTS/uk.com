package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;

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
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private ScheWorkUpdateService scheWork;
	@Override
	public ApplicationReflectOutput preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara) {		
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
					
			//予定開始時刻の反映
			//予定終了時刻の反映
			TimeReflectPara timeData = new TimeReflectPara(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getStartTime(), 
					holidayWorkPara.getHolidayWorkPara().getEndTime(), 
					1, 
					true, 
					true);
			scheWork.updateScheStartEndTime(timeData);
			
			//事前休出時間の反映
			holidayWorkProcess.reflectWorkTimeFrame(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getMapWorkTimeFrame());
			//事前所定外深夜時間の反映
			workUpdate.updateTimeShiftNight(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getNightTime(), true);
			IntegrationOfDaily calculateData = calculate.calculate(overTimeService.calculateForAppReflect(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate()));
			attendanceTime.updateFlush(calculateData.getAttendanceTimeOfDailyPerformance().get());
			return new ApplicationReflectOutput(ReflectedStateRecord.REFLECTED, ReasonNotReflectRecord.ACTUAL_CONFIRMED);
		} catch (Exception e) {
			return new ApplicationReflectOutput(EnumAdaptor.valueOf(holidayWorkPara.getHolidayWorkPara().getReflectedState().value, ReflectedStateRecord.class), 
					holidayWorkPara.getHolidayWorkPara().getReasonNotReflect() == null ? null : EnumAdaptor.valueOf(holidayWorkPara.getHolidayWorkPara().getReasonNotReflect().value, ReasonNotReflectRecord.class));
		}
		
	}

}
