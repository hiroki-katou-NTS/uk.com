package nts.uk.ctx.at.record.dom.daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

public interface DailyRecordAdUpService {
	// ドメインモデル「日別実績の勤務情報」を更新する
	public void adUpWorkInfo(WorkInfoOfDailyPerformance workInfo);

	// ドメインモデル「日別実績の所属情報」を更新する
	public void adUpAffilicationInfo(AffiliationInforOfDailyPerfor affiliationInfor);

	// ドメインモデル「日別実績の計算区分」を更新する
	public void adUpCalAttr(CalAttrOfDailyPerformance calAttr);

	// ドメインモデル「日別実績の勤務種別」を更新する
	public void adUpWorkType(Optional<WorkTypeOfDailyPerformance> businessType);

	// ドメインモデル「日別実績の出退勤」を更新する
	public void adUpTimeLeaving(Optional<TimeLeavingOfDailyPerformance> attendanceLeave);

	// ドメインモデル「日別実績の休憩時間帯」を更新する
	public void adUpBreakTime(List<BreakTimeOfDailyPerformance> breakTime);

	// ドメインモデル「日別実績の外出時間帯」を更新する
	public void adUpOutTime(Optional<OutingTimeOfDailyPerformance> outingTime);

	// ドメインモデル「日別実績の短時間勤務時間帯」を更新する
	public void adUpShortTime(Optional<ShortTimeOfDailyPerformance> shortTime);

	// ドメインモデル「日別実績の臨時出退勤」を更新する
	public void adUpTemporaryTime(Optional<TemporaryTimeOfDailyPerformance> tempTime);

	// ドメインモデル「日別実績の入退門」を更新する
	public void adUpAttendanceLeavingGate(Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate);

	// ドメインモデル「日別実績の勤怠時間」を更新する
	public void adUpAttendanceTime(Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance);

	// ドメインモデル「日別実績の特定日区分」を更新する
	public void adUpSpecificDate(Optional<SpecificDateAttrOfDailyPerfor> specDateAttr);

	// ドメインモデル「日別実績の編集状態」を更新する
	public void adUpEditState(List<EditStateOfDailyPerformance> editState);

	public void clearExcludeEditState(List<EditStateOfDailyPerformance> editState);

	// ドメインモデル「日別実績の任意項目」を更新する
	public void adUpAnyItem(Optional<AnyItemValueOfDaily> anyItemValue);

	//// ドメインモデル「日別実績の作業別勤怠時間」を更新する
	public void adUpAttendanceTimeByWork(Optional<AttendanceTimeByWorkOfDaily> attendancetimeByWork);

	// ドメインモデル「日別実績のPCログオン情報」を更新する
	public void adUpPCLogOn(Optional<PCLogOnInfoOfDaily> pcLogOnInfo);

	// ドメインモデル「日別実績の備考」を更新する
	public void adUpRemark(List<RemarksOfDailyPerform> remarks);

	/**
	 * ドメインモデル「社員の日別実績エラー一覧」を更新する
	 * 
	 * @param errors         domain error
	 * @param lstPairRemove  List<Pair<employeeId, date>>
	 * @param hasRemoveError has remove error
	 */
	public void adUpEmpError(List<EmployeeDailyPerError> errors, List<Pair<String, GeneralDate>> lstPairRemove,
			boolean hasRemoveError);

	public List<IntegrationOfDaily> adTimeAndAnyItemAdUp(List<IntegrationOfDaily> dailys);

	public void removeConfirmApproval(List<IntegrationOfDaily> domainDaily, Optional<IdentityProcessUseSet> iPUSOpt,
			Optional<ApprovalProcessingUseSetting> approvalSet);

	default void addAllDomain(IntegrationOfDaily domain) {

		// ドメインモデル「日別実績の勤務情報」を更新する
		adUpWorkInfo(
				new WorkInfoOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), domain.getWorkInformation()));
		// ドメインモデル「日別実績の所属情報」を更新する
		adUpAffilicationInfo(new AffiliationInforOfDailyPerfor(domain.getEmployeeId(), domain.getYmd(),
				domain.getAffiliationInfor()));

		// ドメインモデル「日別実績の計算区分」を更新する
		adUpCalAttr(new CalAttrOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), domain.getCalAttr()));

		// ドメインモデル「日別実績の出退勤」を更新する
		domain.getAttendanceLeave().ifPresent(x -> adUpTimeLeaving(
				Optional.of(new TimeLeavingOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の休憩時間帯」を更新する
		adUpBreakTime(domain.getBreakTime().stream()
				.map(x -> new BreakTimeOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x))
				.collect(Collectors.toList()));

		// ドメインモデル「日別実績の外出時間帯」を更新する
		adUpOutTime(domain.getOutingTime()
				.map(x -> new OutingTimeOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x)));

		// ドメインモデル「日別実績の短時間勤務時間帯」を更新する
		domain.getShortTime().ifPresent(x -> adUpShortTime(
				Optional.of(new ShortTimeOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の臨時出退勤」を更新する
		domain.getTempTime().ifPresent(x -> adUpTemporaryTime(
				Optional.of(new TemporaryTimeOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の入退門」を更新する
		domain.getAttendanceLeavingGate().ifPresent(x -> adUpAttendanceLeavingGate(
				Optional.of(new AttendanceLeavingGateOfDaily(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の特定日区分」を更新する
		domain.getSpecDateAttr().ifPresent(x -> adUpSpecificDate(
				Optional.of(new SpecificDateAttrOfDailyPerfor(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の編集状態」を更新する
		adUpEditState(domain.getEditState().stream()
				.map(x -> new EditStateOfDailyPerformance(domain.getEmployeeId(), domain.getYmd(), x))
				.collect(Collectors.toList()));

		// ドメインモデル「日別実績のPCログオン情報」を更新する
		domain.getPcLogOnInfo().ifPresent(
				x -> adUpPCLogOn(Optional.of(new PCLogOnInfoOfDaily(domain.getEmployeeId(), domain.getYmd(), x))));

		// ドメインモデル「日別実績の備考」を更新する
		adUpRemark(domain.getRemarks().stream()
				.map(x -> new RemarksOfDailyPerform(domain.getEmployeeId(), domain.getYmd(), x))
				.collect(Collectors.toList()));

		adUpEmpError(domain.getEmployeeError(), new ArrayList<>(), false);

		adTimeAndAnyItemAdUp(Arrays.asList(domain));

	}
}
