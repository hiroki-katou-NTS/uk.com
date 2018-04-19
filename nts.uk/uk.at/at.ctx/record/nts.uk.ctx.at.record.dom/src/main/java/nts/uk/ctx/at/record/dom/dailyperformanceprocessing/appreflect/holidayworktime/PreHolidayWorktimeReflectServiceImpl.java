package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectPara;
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
	@Inject
	private EditStateOfDailyPerformanceRepository dailyReposiroty;
	@Override
	public boolean preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara) {		
		try {
			Optional<WorkInfoOfDailyPerformance> optDailyData = workRepository.find(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate());
			if(!optDailyData.isPresent()) {
				return false;
			}
			IntegrationOfDaily daily =overTimeService.calculateForAppReflect(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate());
			WorkInfoOfDailyPerformance workInformation = daily.getWorkInformation();
			workInformation.setRecordInfo(new WorkInformation(holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), holidayWorkPara.getHolidayWorkPara().getWorkTypeCode()));
			Optional<TimeLeavingOfDailyPerformance> optAttendanceLeave= daily.getAttendanceLeave();
			
			//TimeLeavingOfDailyPerformance attendanceLeavingOfDaily = timeLeavingOfDailyPerformanceRepository.

			WorkStamp attendance = new WorkStamp(new TimeWithDayAttr(holidayWorkPara.getHolidayWorkPara().getStartTime()),
					new TimeWithDayAttr(holidayWorkPara.getHolidayWorkPara().getStartTime()),
					new WorkLocationCD("01"), 
					StampSourceInfo.CORRECTION_RECORD_SET );

			WorkStamp leaving    = new WorkStamp(new TimeWithDayAttr(holidayWorkPara.getHolidayWorkPara().getEndTime()),
					new TimeWithDayAttr(holidayWorkPara.getHolidayWorkPara().getEndTime()),
					new WorkLocationCD("01"),
					StampSourceInfo.CORRECTION_RECORD_SET );

			TimeActualStamp atStamp = new TimeActualStamp(attendance,leaving,1);

			TimeActualStamp leStamp = new TimeActualStamp(attendance,leaving,1);

			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(1),atStamp,leStamp);
			List<TimeLeavingWork> lstTimeLeavingWork = new ArrayList<>();
			lstTimeLeavingWork.add(timeLeavingWork);
			TimeLeavingOfDailyPerformance a = new TimeLeavingOfDailyPerformance(holidayWorkPara.getEmployeeId(), new WorkTimes(1), lstTimeLeavingWork, holidayWorkPara.getBaseDate());
			daily.setAttendanceLeave(Optional.of(a));
			daily.setWorkInformation(workInformation);
			IntegrationOfDaily calculateData = calculate.calculate(daily);
			attendanceTime.updateFlush(calculateData.getAttendanceTimeOfDailyPerformance().get());
			// 予定勤種・就時の反映
			daily = holidayWorkProcess.updateScheWorkTimeType(holidayWorkPara.getEmployeeId(),
					holidayWorkPara.getBaseDate(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTypeCode(), 
					holidayWorkPara.getHolidayWorkPara().getWorkTimeCode(), 
					holidayWorkPara.isScheReflectFlg(), 
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
			List<EditStateOfDailyPerformance> lstEditState = dailyReposiroty.findByKey(holidayWorkPara.getEmployeeId(), holidayWorkPara.getBaseDate());
			daily.setEditState(lstEditState);
			calculateData = calculate.calculate(daily);
			attendanceTime.updateFlush(calculateData.getAttendanceTimeOfDailyPerformance().get());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
