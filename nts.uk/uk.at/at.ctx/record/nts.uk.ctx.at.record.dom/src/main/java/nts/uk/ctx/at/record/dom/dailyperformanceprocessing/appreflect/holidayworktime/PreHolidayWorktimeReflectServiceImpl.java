package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class PreHolidayWorktimeReflectServiceImpl implements PreHolidayWorktimeReflectService{
	@Inject
	private HolidayWorkReflectProcess holidayWorkProcess;
	@Inject
	private WorkUpdateService workUpdate;
	@Inject
	private PreOvertimeReflectService overTimeService;
	@Inject
	private CalculateDailyRecordService calculate;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private WorkUpdateService scheWork;
	@Inject
	private EditStateOfDailyPerformanceRepository dailyReposiroty;
	@Inject
	private AdTimeAndAnyItemAdUpService timeAndAnyItemUpService;
	@Inject
	private AttendanceTimeRepository attendanceTime;
	@Override
	public boolean preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara, boolean isPre) {		
		try {
			IntegrationOfDaily daily = this.createIntegrationOfDailyStart(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), holidayWorkPara.getHolidayWorkPara().getStartTime(), 
					holidayWorkPara.getHolidayWorkPara().getEndTime());
			//IntegrationOfDaily calculateData = calculate.calculate(daily,null);
			// 予定勤種・就時の反映
			daily = holidayWorkProcess.updateScheWorkTimeType(holidayWorkPara.getEmployeeId(),
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.isScheReflectFlg(), isPre,
					holidayWorkPara.getScheAndRecordSameChangeFlg(),
					daily);
			//勤種・就時の反映
			ReflectParameter reflectInfo = new ReflectParameter(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode()); 
			daily = workUpdate.updateWorkTimeTypeHoliwork(reflectInfo, false, daily);
			
			//予定開始時刻の反映
			//予定終了時刻の反映
			TimeReflectPara timeData = new TimeReflectPara(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getStartTime(), 
					holidayWorkPara.getHolidayWorkPara().getEndTime(), 
					1, 
					true, 
					true);
			daily = scheWork.updateScheStartEndTimeHoliday(timeData, daily);
			workRepository.updateByKeyFlush(daily.getWorkInformation());
			
			//事前休出時間の反映
			daily = holidayWorkProcess.reflectWorkTimeFrame(holidayWorkPara.getEmployeeId(), 
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getMapWorkTimeFrame(),
					daily);
			//事前所定外深夜時間の反映
			daily = workUpdate.updateTimeShiftNightHoliday(holidayWorkPara.getEmployeeId(),
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getNightTime(), 
					true, daily);
			attendanceTime.updateFlush(daily.getAttendanceTimeOfDailyPerformance().get());
			
			List<EditStateOfDailyPerformance> lstEditState = dailyReposiroty.findByKey(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate());
			daily.setEditState(lstEditState);
			IntegrationOfDaily calculateData = calculate.calculate(daily,null,null,Optional.empty(),Optional.empty()).getIntegrationOfDaily();
			timeAndAnyItemUpService.addAndUpdate(calculateData);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	@Override
	public IntegrationOfDaily createIntegrationOfDailyStart(String employeeId, GeneralDate baseDate
			, String workTimeCode, String workTypeCode, Integer startTime, Integer endTime) {
		IntegrationOfDaily daily =overTimeService.calculateForAppReflect(employeeId, baseDate);
		if(daily == null) {
			return null;
		}
		WorkInfoOfDailyPerformance workInformation = daily.getWorkInformation();
		workInformation.setRecordInfo(new WorkInformation(workTimeCode, workTypeCode));
		WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(startTime == null ? 0 : startTime),
				new TimeWithDayAttr(startTime == null ? 0 : startTime),
				new WorkLocationCD("01"), 
				StampSourceInfo.CORRECTION_RECORD_SET );

		WorkStamp leaving = new WorkStamp(new TimeWithDayAttr(endTime == null ? 0 : endTime),
				new TimeWithDayAttr(endTime == null ? 0 : endTime),
				new WorkLocationCD("01"),
				StampSourceInfo.CORRECTION_RECORD_SET );

		TimeActualStamp atStamp = new TimeActualStamp(attendance,attendance,1);

		TimeActualStamp leStamp = new TimeActualStamp(leaving,leaving,1);

		TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(1),atStamp,leStamp);
		List<TimeLeavingWork> lstTimeLeavingWork = new ArrayList<>();
		lstTimeLeavingWork.add(timeLeavingWork);
		TimeLeavingOfDailyPerformance a = new TimeLeavingOfDailyPerformance(employeeId, new WorkTimes(1), lstTimeLeavingWork, baseDate);
		daily.setAttendanceLeave(Optional.of(a));
		daily.setWorkInformation(workInformation);
		IntegrationOfDaily calculateData = calculate.calculate(daily,null,null,Optional.empty(),Optional.empty()).getIntegrationOfDaily();
		timeAndAnyItemUpService.addAndUpdate(calculateData);
		return daily;
	}

}
