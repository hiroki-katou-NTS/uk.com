package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerformRepo;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

@Stateless
public class IntegrationOfDailyGetterImpl implements IntegrationOfDailyGetter {

	/** リポジトリ：日別実績の勤怠時間 */
	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;
	
	/** リポジトリ：日別実績の勤務情報 */
	@Inject
	private WorkInformationRepository workInformationRepository;  
	
	/** リポジトリ：日別実績の計算区分 */
	@Inject
	private CalAttrOfDailyPerformanceRepository calAttrOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の所属情報 */
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;
	
	/** リポジトリ：日別実績のPCログオン情報 */
	@Inject
	private PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo; 
	
	/** リポジトリ:社員の日別実績エラー一覧 */
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	
	/** リポジトリ：日別実績の外出時間帯 */
	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の休憩時間帯 */
	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository; 
	
	/** リポジトリ：日別実績の出退勤 */
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の短時間勤務時間帯 */
	@Inject
	private ShortTimeOfDailyPerformanceRepository shortTimeOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の特定日区分 */
	@Inject
	private SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDailyPerforRepo;
	
	/** リポジトリ：日別実績の入退門 */
	@Inject
	private AttendanceLeavingGateOfDailyRepo attendanceLeavingGateOfDailyRepo;
	
	/** リポジトリ：日別実績の任意項目 */
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;
	
	/** リポジトリ：日別実績のの編集状態 */
	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の臨時出退勤 */
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;
	
	/** リポジトリ：日別実績の備考 */
	@Inject
	private RemarksOfDailyPerformRepo remarksRepository;
	
	@Inject
	private DailySnapshotWorkAdapter snapshotAdapter;
	
	
	/**
	 * 日別実績(WORK)の作成
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	@Override
	public List<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, DatePeriod datePeriod) {
		
		val attendanceTimeList= workInformationRepository.findByPeriodOrderByYmd(employeeId, datePeriod);
		
		List<IntegrationOfDaily> returnList = new ArrayList<>();

		for(WorkInfoOfDailyPerformance attendanceTime : attendanceTimeList) {
			/** リポジトリ：日別実績の勤務情報 */
			val workInf = workInformationRepository.find(employeeId, attendanceTime.getYmd());  
			/** リポジトリ：日別実績.日別実績の計算区分 */
			val calAttr = calAttrOfDailyPerformanceRepository.find(employeeId, attendanceTime.getYmd());
			
			/** リポジトリ：日別実績の所属情報 */
			val affiInfo = affiliationInforOfDailyPerforRepository.findByKey(employeeId, attendanceTime.getYmd());

			if(!workInf.isPresent() || !affiInfo.isPresent())//calAttr == null
				continue;
			workInf.get().getWorkInformation().setVer(workInf.get().getVersion());
			/** リポジトリ：日別実績のPCログオン情報 */
			Optional<PCLogOnInfoOfDaily> pCLogOnInfoOfDaily = pcLogOnInfoOfDailyRepo.find(employeeId, attendanceTime.getYmd());
			Optional<PCLogOnInfoOfDailyAttd> pCLogOnInfoOfDailyAttd = pCLogOnInfoOfDaily.isPresent()?Optional.of(pCLogOnInfoOfDaily.get().getTimeZone()):Optional.empty();
			/** リポジトリ：日別実績の外出時間帯 */
			Optional<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance = outingTimeOfDailyPerformanceRepository.findByEmployeeIdAndDate(employeeId, attendanceTime.getYmd());
			Optional<OutingTimeOfDailyAttd> outingTimeOfDailyAttd = outingTimeOfDailyPerformance.isPresent()?Optional.of(outingTimeOfDailyPerformance.get().getOutingTime()):Optional.empty();
			/** リポジトリ：日別実績の休憩時間帯 */
			Optional<BreakTimeOfDailyPerformance> listBreakTimeOfDailyPerformance = breakTimeOfDailyPerformanceRepository.findByKey(employeeId, attendanceTime.getYmd());
			
			Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance = attendanceTimeRepository.find(employeeId, attendanceTime.getYmd());
			Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyAttd = attendanceTimeOfDailyPerformance.isPresent()?Optional.of(attendanceTimeOfDailyPerformance.get().getTime()):Optional.empty();
			/** リポジトリ：日別実績の出退勤 */
			Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = timeLeavingOfDailyPerformanceRepository.findByKey(employeeId, attendanceTime.getYmd());
			Optional<TimeLeavingOfDailyAttd> timeLeavingOfDailyAttd = timeLeavingOfDailyPerformance.isPresent()?Optional.of(timeLeavingOfDailyPerformance.get().getAttendance()):Optional.empty();
			/** リポジトリ：日別実績の短時間勤務時間帯 */
			Optional<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformance = shortTimeOfDailyPerformanceRepository.find(employeeId, attendanceTime.getYmd());
			Optional<ShortTimeOfDailyAttd> shortTimeOfDailyAttd = shortTimeOfDailyPerformance.isPresent()?Optional.of(shortTimeOfDailyPerformance.get().getTimeZone()):Optional.empty();
			/** リポジトリ：日別実績の特定日区分 */
			Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrOfDailyPerfor = specificDateAttrOfDailyPerforRepo.find(employeeId, attendanceTime.getYmd());
			Optional<SpecificDateAttrOfDailyAttd> specificDateAttrOfDailyAttd = specificDateAttrOfDailyPerfor.isPresent()?Optional.of(specificDateAttrOfDailyPerfor.get().getSpecificDay()):Optional.empty();
			/** リポジトリ：日別実績の入退門 */
			Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDaily = attendanceLeavingGateOfDailyRepo.find(employeeId, attendanceTime.getYmd());
			Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGateOfDailyAttd = attendanceLeavingGateOfDaily.isPresent()?Optional.of(attendanceLeavingGateOfDaily.get().getTimeZone()):Optional.empty();
			/** リポジトリ：日別実績の任意項目 */
			Optional<AnyItemValueOfDaily> anyItemValueOfDaily = anyItemValueOfDailyRepo.find(employeeId, attendanceTime.getYmd());
			Optional<AnyItemValueOfDailyAttd> anyItemValueOfDailyAttd = anyItemValueOfDaily.isPresent()?Optional.of(anyItemValueOfDaily.get().getAnyItem()):Optional.empty();
			/** リポジトリ：日別実績のの編集状態 */
			List<EditStateOfDailyPerformance> listEditStateOfDailyPerformance = editStateOfDailyPerformanceRepository.findByKey(employeeId, attendanceTime.getYmd());
			/** リポジトリ：日別実績の臨時出退勤 */
			Optional<TemporaryTimeOfDailyPerformance>  temporaryTimeOfDailyPerformance = temporaryTimeOfDailyPerformanceRepository.findByKey(employeeId, attendanceTime.getYmd());
			Optional<TemporaryTimeOfDailyAttd> temporaryTimeOfDailyAttd = temporaryTimeOfDailyPerformance.isPresent()?Optional.of(temporaryTimeOfDailyPerformance.get().getAttendance()):Optional.empty();
			
			List<RemarksOfDailyPerform> listRemarksOfDailyPerform = remarksRepository.getRemarks(employeeId, attendanceTime.getYmd());
			
			val snapshot = snapshotAdapter.find(employeeId, attendanceTime.getYmd());
			
			returnList.add(
				new IntegrationOfDaily(
					attendanceTime.getEmployeeId(),
					attendanceTime.getYmd(),
					workInf.get().getWorkInformation(),
					calAttr.getCalcategory(),
					affiInfo.get().getAffiliationInfor(),
					pCLogOnInfoOfDailyAttd,
					employeeDailyPerErrorRepository.find(employeeId, attendanceTime.getYmd()),/** リポジトリ:社員の日別実績エラー一覧 */
					outingTimeOfDailyAttd,
					listBreakTimeOfDailyPerformance.map(c->c.getTimeZone()),
					attendanceTimeOfDailyAttd,
//						attendanceTimeByWorkOfDailyRepository.find(employeeId, attendanceTime.getYmd()),/** リポジトリ：日別実績の作業別勤怠時間 */
					timeLeavingOfDailyAttd,
					shortTimeOfDailyAttd,
					specificDateAttrOfDailyAttd,
					attendanceLeavingGateOfDailyAttd,
					anyItemValueOfDailyAttd,/** リポジトリ：日別実績の任意項目 */
					listEditStateOfDailyPerformance.stream().map(c->c.getEditState()).collect(Collectors.toList()),
					temporaryTimeOfDailyAttd,
					listRemarksOfDailyPerform.stream().map(c->c.getRemarks()).collect(Collectors.toList()),
					snapshot.map(c -> c.getSnapshot().toDomain())
					));
		}
		return returnList;
	}

}
