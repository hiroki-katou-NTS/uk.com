package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.StoredProcedureFactory;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の勤怠時間と任意項目を同時更新し、ストアドを実行するためのサービス
 * @author keisuke_hoshina
 *
 */
@Stateless
public class AdTimeAndAnyItemAdUpServiceImpl implements AdTimeAndAnyItemAdUpService {

	/*日別実績の勤怠時間*/
	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;
	
	/*任意項目リポジトリ*/
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;
	
	/*日別実績の勤務情報*/
	@Inject
	private WorkInformationRepository workInfo;
	/*日別実績のPCログオンログオフ*/
	@Inject
	private PCLogOnInfoOfDailyRepo pcLogon;
	/*日別実績の入退門*/
	@Inject
	private AttendanceLeavingGateOfDailyRepo attendanceGate;
	/*日別実績の出退勤*/
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeave;
	
	/*任意項目ストアド*/
	@Inject
	private StoredProcedureFactory dbStoredProcess;
	
	@Override
	public void addAndUpdate(String empId ,GeneralDate ymd,
							Optional<AttendanceTimeOfDailyPerformance> attendanceTime, Optional<AnyItemValueOfDaily> anyItem) {
		workInfo.find(empId, ymd).ifPresent(wi -> {
			Optional<PCLogOnInfoOfDaily> pc = pcLogon.find(empId, ymd);
			Optional<AttendanceLeavingGateOfDaily> al = attendanceGate.find(empId, ymd);
			Optional<TimeLeavingOfDailyPerformance> tl = timeLeave.findByKey(empId, ymd);
			wi.changeCalcState(CalculationState.Calculated);
			IntegrationOfDaily daily = new IntegrationOfDaily(
					empId,
					ymd,
					wi.getWorkInformation(), //workInformation
					null, //calAttr
					null, //affiliationInfor
					pc.isPresent()?Optional.of(pc.get().getTimeZone()):Optional.empty(),//pcLogOnInfo
					new ArrayList<>(),//employeeError
					Optional.empty(),//outingTime
					new ArrayList<>(),//breakTime
					attendanceTime.isPresent()?Optional.of(attendanceTime.get().getTime()):Optional.empty(),//attendanceTimeOfDailyPerformance
					tl.isPresent()?Optional.of(tl.get().getAttendance()):Optional.empty(),//attendanceLeave
					Optional.empty(), //shortTime
					Optional.empty(), //specDateAttr
					al.isPresent()?Optional.of(al.get().getTimeZone()):Optional.empty(),//attendanceLeavingGate
					anyItem.isPresent()?Optional.of(anyItem.get().getAnyItem()):Optional.empty(), //anyItemValue
					new ArrayList<>(),//editState
					Optional.empty(), //tempTime
					new ArrayList<>());//remarks
			addAndUpdate(daily);
		});
	}
	
	@Override
	public List<IntegrationOfDaily> addAndUpdate(List<IntegrationOfDaily> daily) {
		return saveOnly(daily);
	}
	
	@Override
	public IntegrationOfDaily addAndUpdate(IntegrationOfDaily daily) {
		return addAndUpdate(Arrays.asList(daily)).get(0);
	}

	@Override
	public List<IntegrationOfDaily> saveOnly(List<IntegrationOfDaily> daily) {
		String comId = AppContexts.user().companyId();
		
		daily.stream().forEach(d -> {
			//勤怠時間更新
			d.getAttendanceTimeOfDailyPerformance().ifPresent(at -> {
				attendanceTimeRepository
						.update(new AttendanceTimeOfDailyPerformance(d.getEmployeeId(), d.getYmd(), at));
			});
			//任意項目更新
			d.getAnyItemValue().ifPresent(ai -> {
				anyItemValueOfDailyRepo.persistAndUpdate(new AnyItemValueOfDaily(d.getEmployeeId(), d.getYmd(), ai));
			});
			Optional<AttendanceTimeOfDailyPerformance> attdTimeOfDailyPer = d
					.getAttendanceTimeOfDailyPerformance().isPresent()
							? Optional.of(new AttendanceTimeOfDailyPerformance(d.getEmployeeId(), d.getYmd(),
									d.getAttendanceTimeOfDailyPerformance().get()))
							: Optional.empty(); 
			dbStoredProcess.runStoredProcedure(comId, attdTimeOfDailyPer, new WorkInfoOfDailyPerformance(d.getEmployeeId(), d.getYmd(), d.getWorkInformation()) );
		});
		return daily;
	}

}
