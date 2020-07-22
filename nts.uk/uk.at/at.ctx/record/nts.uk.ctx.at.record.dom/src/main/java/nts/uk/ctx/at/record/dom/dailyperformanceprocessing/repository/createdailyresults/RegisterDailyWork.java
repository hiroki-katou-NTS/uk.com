package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
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
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.context.AppContexts;

/**
 * 登録する (new_2020)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.登録する (Đăng ký).登録する
 * @author tutk
 *
 */
@Stateless
public class RegisterDailyWork {
	
	@Inject
	private InsertWorkInfoOfDailyPerforService insertWorkInfoOfDailyPerforService;
	
	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;
	
	@Inject
	private StampDakokuRepository stampDakokuRepository;
	
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
		List<BreakTimeOfDailyPerformance> breakTimes = integrationOfDaily.getBreakTime().stream()
				.map(c -> new BreakTimeOfDailyPerformance(employeeId, ymd, c)).collect(Collectors.toList());
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
			dailyRecordAdUpService.adUpTimeLeaving(Optional.of(
					new TimeLeavingOfDailyPerformance(employeeId, ymd, integrationOfDaily.getAttendanceLeave().get())));
		}
		
		//日別実績の臨時出退勤を登録する
		if (integrationOfDaily.getTempTime().isPresent()) {
			dailyRecordAdUpService.adUpTemporaryTime(Optional
					.of(new TemporaryTimeOfDailyPerformance(employeeId, ymd, integrationOfDaily.getTempTime().get())));
		}
		
		//日別実績の外出時間帯を登録する
		if (integrationOfDaily.getOutingTime().isPresent()) {
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
	}

}
