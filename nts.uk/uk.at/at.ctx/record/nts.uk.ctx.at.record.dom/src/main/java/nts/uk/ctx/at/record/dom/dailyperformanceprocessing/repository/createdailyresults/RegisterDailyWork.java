package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.SnapshotImport;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo.InsertWorkInfoOfDailyPerforService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.shr.com.context.AppContexts;

/**
 * 登録する (new_2020)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.登録する (Đăng ký).登録する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterDailyWork {
	
	@Inject
	private InsertWorkInfoOfDailyPerforService insertWorkInfoOfDailyPerforService;
	
	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;
	
	@Inject
	private StampDakokuRepository stampDakokuRepository;
	
	@Inject
	private DailySnapshotWorkAdapter snapshotAdapter;
	
	public void register(IntegrationOfDaily integrationOfDaily,List<Stamp> listStamp) {
		String companyId = AppContexts.user().companyId();
		String employeeId = integrationOfDaily.getEmployeeId();
		GeneralDate ymd =  integrationOfDaily.getYmd();
		
		//ドメインモデル「日別実績の勤務情報」を更新する (Update 「日別実績の勤務情報」)
		if(integrationOfDaily.getWorkInformation() !=null) {
			WorkInfoOfDailyPerformance workInfoOfDailyPer = new WorkInfoOfDailyPerformance(employeeId, ymd,
					integrationOfDaily.getWorkInformation());
			this.insertWorkInfoOfDailyPerforService.updateWorkInfoOfDailyPerforService(companyId, employeeId, ymd,
					workInfoOfDailyPer);
		}
		
		//日別実績の所属情報を登録する
		if (integrationOfDaily.getAffiliationInfor() != null) {
			AffiliationInforOfDailyPerfor dailyPerfor = new AffiliationInforOfDailyPerfor(employeeId, ymd,
					integrationOfDaily.getAffiliationInfor());
			dailyRecordAdUpService.adUpAffilicationInfo(dailyPerfor);
		}
		
		//日別実績の休憩時間帯を登録する
		Optional<BreakTimeOfDailyPerformance> breakTimes = integrationOfDaily.getBreakTime()
				.map(c -> new BreakTimeOfDailyPerformance(employeeId, ymd, c));
		dailyRecordAdUpService.adUpBreakTime(breakTimes);
		
		//日別実績の勤務種別を登録する (đã xóa nên k insert)
		
		//日別実績の特定日区分を登録する
		if (integrationOfDaily.getSpecDateAttr().isPresent()) {
			dailyRecordAdUpService.adUpSpecificDate(Optional.of(
					new SpecificDateAttrOfDailyPerfor(employeeId, ymd, integrationOfDaily.getSpecDateAttr().get())));
		}
		
		//日別実績の計算区分を登録する
		if (integrationOfDaily.getCalAttr() != null) {
			CalAttrOfDailyPerformance attrOfDailyPerformance = new CalAttrOfDailyPerformance(employeeId, ymd,
					integrationOfDaily.getCalAttr());
			dailyRecordAdUpService.adUpCalAttr(attrOfDailyPerformance);
		}
		
		//日別実績の出退勤を登録する
		if (integrationOfDaily.getAttendanceLeave().isPresent()
				&& integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks() != null
				&& !integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks().isEmpty()) {
			List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks();
			timeLeavingWorks = checkExist(timeLeavingWorks);
			integrationOfDaily.getAttendanceLeave().get().setTimeLeavingWorks(timeLeavingWorks);
			dailyRecordAdUpService.adUpTimeLeaving(Optional.of(
					new TimeLeavingOfDailyPerformance(employeeId, ymd, integrationOfDaily.getAttendanceLeave().get())));
		}
		
		//日別実績の臨時出退勤を登録する
		if (integrationOfDaily.getTempTime().isPresent()) {
			
			List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getTempTime().get().getTimeLeavingWorks();
			timeLeavingWorks = checkExist(timeLeavingWorks);
			integrationOfDaily.getTempTime().get().setTimeLeavingWorks(timeLeavingWorks);
			dailyRecordAdUpService.adUpTemporaryTime(Optional
					.of(new TemporaryTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getTempTime().get())));
		}
		
		//日別実績の外出時間帯を登録する
		if (integrationOfDaily.getOutingTime().isPresent()) {
			List<OutingTimeSheet> listOutingTimeSheet = checkExistOuting(integrationOfDaily.getOutingTime().get().getOutingTimeSheets());
			integrationOfDaily.getOutingTime().get().setOutingTimeSheets(listOutingTimeSheet);
			dailyRecordAdUpService.adUpOutTime(Optional
					.of(new OutingTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getOutingTime().get())));
		}
		
		//日別実績の短時間勤務時間帯を登録する
		if (integrationOfDaily.getShortTime().isPresent()) {
			dailyRecordAdUpService.adUpShortTime(Optional
					.of(new ShortTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getShortTime().get())));
		}
		
		//日別実績の入退門を登録する
		if (integrationOfDaily.getAttendanceLeavingGate().isPresent()
				&& integrationOfDaily.getAttendanceLeavingGate().get().getAttendanceLeavingGates() != null
				&& !integrationOfDaily.getAttendanceLeavingGate().get().getAttendanceLeavingGates().isEmpty()) {
			dailyRecordAdUpService.adUpAttendanceLeavingGate(Optional.of(new AttendanceLeavingGateOfDaily(employeeId,
					ymd, integrationOfDaily.getAttendanceLeavingGate().get())));
		}

		// 日別実績のPCログオン情報を登録する
		if (integrationOfDaily.getPcLogOnInfo().isPresent()
				&& integrationOfDaily.getPcLogOnInfo().get().getLogOnInfo() != null
				&& !integrationOfDaily.getPcLogOnInfo().get().getLogOnInfo().isEmpty()) {
			dailyRecordAdUpService.adUpPCLogOn(
					Optional.of(new PCLogOnInfoOfDaily(employeeId, ymd, integrationOfDaily.getPcLogOnInfo().get())));
		}
		
		//ドメインモデル「打刻」を更新する (Update 「打刻」)
		if (!listStamp.isEmpty()) {
			listStamp.forEach(stampItem -> {
				this.stampDakokuRepository.update(stampItem);
				
			});
		}
		
		//ドメインモデル「社員の日別実績エラー一覧」を更新する
		if (integrationOfDaily.getEmployeeError() != null && !integrationOfDaily.getEmployeeError().isEmpty()) {
			List<EmployeeDailyPerError> errors = integrationOfDaily.getEmployeeError().stream()
					.filter(x -> x != null).collect(Collectors.toList());
			dailyRecordAdUpService.adUpEmpError(integrationOfDaily.getEmployeeError(),
					errors.stream().map(x -> Pair.of(x.getEmployeeID(), x.getDate())).collect(Collectors.toList()),
					true);
		}
		
		integrationOfDaily.getSnapshot().ifPresent(ss -> {
			val oldSnapshot = snapshotAdapter.find(integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd());
			if (!oldSnapshot.isPresent()) {
				snapshotAdapter.save(DailySnapshotWorkImport.builder()
						.sid(integrationOfDaily.getEmployeeId())
						.ymd(integrationOfDaily.getYmd())
						.snapshot(SnapshotImport.builder()
											.workTime(ss.getWorkInfo().getWorkTimeCodeNotNull().map(c -> c.v()))
											.workType(ss.getWorkInfo().getWorkTypeCode().v())
											.predetermineTime(ss.getPredetermineTime().v())
											.build())
						.build());
			}
		});
	}
	private List<TimeLeavingWork> checkExist(List<TimeLeavingWork> listTimeLeavingWork){
		List<TimeLeavingWork> datas = new ArrayList<>();
		for(TimeLeavingWork item :listTimeLeavingWork) {
			boolean check = false;
			if(item.getAttendanceStamp().isPresent() && 
					(checkWorkStamp(item.getAttendanceStamp().get().getActualStamp())
					|| checkWorkStamp(item.getAttendanceStamp().get().getStamp()))){
				check = true;
			}
			
			if(item.getLeaveStamp().isPresent() &&
					(checkWorkStamp(item.getLeaveStamp().get().getStamp()) 
					|| checkWorkStamp(item.getLeaveStamp().get().getStamp()))) {
				check = true;
			}
			if(check) {
				datas.add(item);
			}
		}
		
		return datas;
	}
	
	private List<OutingTimeSheet> checkExistOuting(List<OutingTimeSheet> listOutingTimeSheet){
		List<OutingTimeSheet> datas = new ArrayList<>();
		for(OutingTimeSheet ots : listOutingTimeSheet) {
			if(ots.getComeBack().isPresent() || ots.getGoOut().isPresent()) {
				datas.add(ots);
			}
		}
		return datas;
	}
	
	private boolean checkWorkStamp (Optional<WorkStamp> ws) {
		if(ws.isPresent() && ws.get().getTimeDay().getTimeWithDay().isPresent() && ws.get().getTimeDay().getTimeWithDay().get().v() !=0) {
			return true;
		}
		return false;
	}

}
