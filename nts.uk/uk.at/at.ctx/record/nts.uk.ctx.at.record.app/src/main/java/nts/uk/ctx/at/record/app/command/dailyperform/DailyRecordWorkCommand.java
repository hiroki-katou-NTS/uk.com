package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.PCLogInfoOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.erroralarm.EmployeeDailyPerErrorCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.remark.RemarkOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.snapshot.SnapshotOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

public class DailyRecordWorkCommand extends DailyWorkCommonCommand {
	
	private final List<ItemValue> itemValues = new ArrayList<>();

	/** 勤務情報： 日別実績の勤務情報 */
	@Getter
	private final WorkInformationOfDailyPerformCommand workInfo = new WorkInformationOfDailyPerformCommand();

	/** 計算区分： 日別実績の計算区分 */
	@Getter
	private final CalcAttrOfDailyPerformanceCommand calcAttr = new CalcAttrOfDailyPerformanceCommand();

	/** 所属情報： 日別実績の所属情報 */
	@Getter
	private final AffiliationInforOfDailyPerformCommand affiliationInfo = new AffiliationInforOfDailyPerformCommand();
	
	/** スナップショット */
	@Getter
	private final SnapshotOfDailyPerformCommand snapshot = new SnapshotOfDailyPerformCommand();

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// TODO: list?
	@Getter
	private final EmployeeDailyPerErrorCommand errors = new EmployeeDailyPerErrorCommand();

	/** 外出時間帯: 日別実績の外出時間帯 */
	@Getter
	private final OutingTimeOfDailyPerformanceCommand outingTime = new OutingTimeOfDailyPerformanceCommand();

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@Getter
	private final BreakTimeOfDailyPerformanceCommand breakTime = new BreakTimeOfDailyPerformanceCommand();

	/** 勤怠時間: 日別実績の勤怠時間 */
	@Getter
	private final AttendanceTimeOfDailyPerformCommand attendanceTime = new AttendanceTimeOfDailyPerformCommand();

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@Getter
	private final AttendanceTimeByWorkOfDailyCommand attendanceTimeByWork = new AttendanceTimeByWorkOfDailyCommand();

	/** 出退勤: 日別実績の出退勤 */
	@Getter
	private final TimeLeavingOfDailyPerformanceCommand timeLeaving = new TimeLeavingOfDailyPerformanceCommand();

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@Getter
	private final ShortTimeOfDailyCommand shortWorkTime = new ShortTimeOfDailyCommand();

	/** 特定日区分: 日別実績の特定日区分 */
	@Getter
	private final SpecificDateAttrOfDailyCommand specificDateAttr = new SpecificDateAttrOfDailyCommand();

	/** 入退門: 日別実績の入退門 */
	@Getter
	private final AttendanceLeavingGateOfDailyCommand attendanceLeavingGate = new AttendanceLeavingGateOfDailyCommand();

	/** 任意項目: 日別実績の任意項目 */
	@Getter
	private final OptionalItemOfDailyPerformCommand optionalItem = new OptionalItemOfDailyPerformCommand();

	/** 編集状態: 日別実績の編集状態 */
	@Getter
	private final EditStateOfDailyPerformCommand editState = new EditStateOfDailyPerformCommand();

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@Getter
	private final TemporaryTimeOfDailyPerformanceCommand temporaryTime = new TemporaryTimeOfDailyPerformanceCommand();

	/** PCログオン情報: 日別実績のPCログオン情報 */
	@Getter
	private final PCLogInfoOfDailyCommand pcLogInfo = new PCLogInfoOfDailyCommand();
	
	/** PCログオン情報: 日別実績のPCログオン情報 */
	@Getter
	private final RemarkOfDailyCommand remarks = new RemarkOfDailyCommand();

	public DailyWorkCommonCommand getCommand(String layout) {
		switch (layout) {
		case DAILY_WORK_INFO_CODE:
			return this.workInfo;
		case DAILY_CALCULATION_ATTR_CODE:
			return this.calcAttr;
		case DAILY_AFFILIATION_INFO_CODE:
			return this.affiliationInfo;
		case DAILY_SNAPSHOT_CODE:
			return this.snapshot;
		case DAILY_OUTING_TIME_CODE:
			return this.outingTime;
		case DAILY_BREAK_TIME_CODE:
			return this.breakTime;
		case DAILY_ATTENDANCE_TIME_CODE:
			return this.attendanceTime;
		case DAILY_ATTENDANCE_TIME_BY_WORK_CODE:
			return this.attendanceTimeByWork;
		case DAILY_ATTENDACE_LEAVE_CODE:
			return this.timeLeaving;
		case DAILY_SHORT_TIME_CODE:
			return this.shortWorkTime;
		case DAILY_SPECIFIC_DATE_ATTR_CODE:
			return this.specificDateAttr;
		case DAILY_ATTENDANCE_LEAVE_GATE_CODE:
			return this.attendanceLeavingGate;
		case DAILY_OPTIONAL_ITEM_CODE:
			return this.optionalItem;
		case DAILY_EDIT_STATE_CODE:
			return this.editState;
		case DAILY_TEMPORARY_TIME_CODE:
			return this.temporaryTime;
		case DAILY_PC_LOG_INFO_CODE:
			return this.pcLogInfo;
		case DAILY_REMARKS_CODE:
			return this.remarks;
		default:
			return null;
		}
	}
	
	public List<String> getAvailableLayout(){
		return Arrays.asList(DAILY_WORK_INFO_CODE, DAILY_CALCULATION_ATTR_CODE, DAILY_AFFILIATION_INFO_CODE,
				DAILY_SNAPSHOT_CODE, DAILY_OUTING_TIME_CODE, DAILY_BREAK_TIME_CODE, DAILY_ATTENDANCE_TIME_CODE,
				DAILY_ATTENDANCE_TIME_BY_WORK_CODE, DAILY_ATTENDACE_LEAVE_CODE, DAILY_SHORT_TIME_CODE,
				DAILY_SPECIFIC_DATE_ATTR_CODE, DAILY_ATTENDANCE_LEAVE_GATE_CODE, DAILY_OPTIONAL_ITEM_CODE,
				DAILY_EDIT_STATE_CODE, DAILY_TEMPORARY_TIME_CODE, DAILY_PC_LOG_INFO_CODE, DAILY_REMARKS_CODE);
	}

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		DailyRecordDto fullDto = (DailyRecordDto) item;
		this.workInfo.setRecords(fullDto.getWorkInfo());
		this.calcAttr.setRecords(fullDto.getCalcAttr());
		this.affiliationInfo.setRecords(fullDto.getAffiliationInfo());
		fullDto.getErrors().stream().forEach(c -> this.errors.setRecords(c));
		this.outingTime.setRecords(fullDto.getOutingTime().orElse(null));
		this.breakTime.setRecords(fullDto.getBreakTime().orElse(null));
		this.attendanceTime.setRecords(fullDto.getAttendanceTime().orElse(null));
		this.attendanceTimeByWork.setRecords(fullDto.getAttendanceTimeByWork().orElse(null));
		this.timeLeaving.setRecords(fullDto.getTimeLeaving().orElse(null));
		this.shortWorkTime.setRecords(fullDto.getShortWorkTime().orElse(null));
		this.specificDateAttr.setRecords(fullDto.getSpecificDateAttr().orElse(null));
		this.attendanceLeavingGate.setRecords(fullDto.getAttendanceLeavingGate().orElse(null));
		this.optionalItem.setRecords(fullDto.getOptionalItem().orElse(null));
		fullDto.getEditStates().stream().forEach(c -> this.editState.setRecords(c));
		this.temporaryTime.setRecords(fullDto.getTemporaryTime().orElse(null));
		this.pcLogInfo.setRecords(fullDto.getPcLogInfo().orElse(null));
		fullDto.getRemarks().stream().forEach(c -> this.remarks.setRecords(c));
		this.snapshot.setRecords(fullDto.getSnapshot().orElse(null));
	}

	@Override
	public void forEmployee(String employeId) {
		super.forEmployee(employeId);
		this.attendanceTime.forEmployee(employeId);
		this.workInfo.forEmployee(employeId);
		this.calcAttr.forEmployee(employeId);
		this.affiliationInfo.forEmployee(employeId);
		this.snapshot.forEmployee(employeId);
		this.errors.forEmployee(employeId);
		this.outingTime.forEmployee(employeId);
		this.breakTime.forEmployee(employeId);
		this.attendanceTimeByWork.forEmployee(employeId);
		this.timeLeaving.forEmployee(employeId);
		this.shortWorkTime.forEmployee(employeId);
		this.specificDateAttr.forEmployee(employeId);
		this.attendanceLeavingGate.forEmployee(employeId);
		this.optionalItem.forEmployee(employeId);
		this.editState.forEmployee(employeId);
		this.temporaryTime.forEmployee(employeId);
		this.pcLogInfo.forEmployee(employeId);
		this.remarks.forEmployee(employeId);
	}

	@Override
	public void withDate(GeneralDate date) {
		super.withDate(date);
		this.attendanceTime.withDate(date);
		this.workInfo.withDate(date);
		this.calcAttr.withDate(date);
		this.affiliationInfo.withDate(date);
		this.snapshot.withDate(date);
		this.errors.withDate(date);
		this.outingTime.withDate(date);
		this.breakTime.withDate(date);
		this.attendanceTimeByWork.withDate(date);
		this.timeLeaving.withDate(date);
		this.shortWorkTime.withDate(date);
		this.specificDateAttr.withDate(date);
		this.attendanceLeavingGate.withDate(date);
		this.optionalItem.withDate(date);
		this.editState.withDate(date);
		this.temporaryTime.withDate(date);
		this.pcLogInfo.withDate(date);
		this.remarks.withDate(date);
	}

	public DailyRecordDto toDto() {
		return DailyRecordDto.builder()
				.breakTimeO(breakTime.toDto())
				.editStates(editState.toDto())
				.attendanceLeavingGateO(attendanceLeavingGate.toDto())
				.attendanceTimeO(attendanceTime.toDto())
				.attendanceTimeByWorkO(attendanceTimeByWork.toDto())
				.employeeId(getEmployeeId())
				.optionalItemsO(optionalItem.toDto())
				.outingTimeO(outingTime.toDto())
				.pcLogInfoO(pcLogInfo.toDto())
				.shortWorkTimeO(shortWorkTime.toDto())
				.remarks(remarks.toDto())
				.specificDateAttrO(specificDateAttr.toDto())
				.temporaryTimeO(temporaryTime.toDto())
				.timeLeavingO(timeLeaving.toDto())
				.withAffiliationInfo(affiliationInfo.toDto())
				.withCalcAttr(calcAttr.toDto())
//				.withErrors(errors.getData())
				.withWorkInfo(workInfo.toDto())
				.workingDate(getWorkDate())
				.withSnapshot(snapshot.toDto().orElse(null))
				.complete();
	}

	public static DailyRecordWorkCommand open() {
		return new DailyRecordWorkCommand();
	}

	public DailyRecordWorkCommand forEmployeeId(String employeeId) {
		this.forEmployee(employeeId);
		return this;
	}

	public DailyRecordWorkCommand withWokingDate(GeneralDate workDate) {
		this.withDate(workDate);
		return this;
	}
	
	public DailyRecordWorkCommand forEmployeeIdAndDate(String employeeId, GeneralDate date) {
		this.forEmployee(employeeId);
		this.withDate(date);
		return this;
	}

	public DailyRecordWorkCommand fromItems(List<ItemValue> itemValues) {
		this.itemValues.addAll(itemValues);
		return this;
	}

	public DailyRecordWorkCommand withData(DailyRecordDto data) {
		this.setRecords(data);
		return this;
	}
	
	public IntegrationOfDaily toDomain() {
		Optional<PCLogOnInfoOfDailyAttd> pC = this.getPcLogInfo().toDomain().map(c -> c.getTimeZone());
				
		Optional<OutingTimeOfDailyAttd> outing = this.getOutingTime().toDomain().map(c -> c.getOutingTime());
				
		Optional<BreakTimeOfDailyAttd> breakTime = this.getBreakTime().toDomain().map(c -> c.getTimeZone());
				
		Optional<AttendanceTimeOfDailyAttendance> attendanceTime = this.getAttendanceTime().toDomain().map(c -> c.getTime());
				
		Optional<TimeLeavingOfDailyAttd> timeLeaving = this.getTimeLeaving().toDomain().map(c -> c.getAttendance());
				
		Optional<ShortTimeOfDailyAttd> shortTime = this.getShortWorkTime().toDomain().map(c -> c.getTimeZone());
				
		Optional<SpecificDateAttrOfDailyAttd> specificDate = this.getSpecificDateAttr().toDomain().map(c -> c.getSpecificDay());
				
		Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate = this.getAttendanceLeavingGate().toDomain().map(c -> c.getTimeZone());
				
		Optional<AnyItemValueOfDailyAttd> anyItem = this.getOptionalItem().toDomain().map(c -> c.getAnyItem());
				
		Optional<TemporaryTimeOfDailyAttd> temporaryTime = this.getTemporaryTime().toDomain().map(c -> c.getAttendance());
				
		Optional<SnapShot> snapshot = this.getSnapshot().toDomain();
		return new IntegrationOfDaily(
										this.getAffiliationInfo().getEmployeeId(),
										this.getAffiliationInfo().getWorkDate(),
										this.getWorkInfo().toDomain().getWorkInformation(), 
										this.getCalcAttr().toDomain().getCalcategory(), 
										this.getAffiliationInfo().toDomain().getAffiliationInfor(),
										pC, //pcLogOnInfo
										this.getErrors().toDomain(), //employeeError
										outing, //outingTime
										breakTime, //breakTime
										attendanceTime, //attendanceTimeOfDailyPerformance
										timeLeaving, //attendanceLeave
										shortTime, //shortTime
										specificDate, //specDateAttr
										attendanceLeavingGate, //attendanceLeavingGate
										anyItem, //anyItemValue
										this.getEditState().toDomain().stream().map(c->c.getEditState()).collect(Collectors.toList()), 
										temporaryTime,
										this.getRemarks().toDomain().stream().map(c->c.getRemarks()).collect(Collectors.toList()),
										snapshot);
	}

	public List<ItemValue> itemValues() {
		return new ArrayList<>(this.itemValues);
	}

	@Override
	public void updateData(Object data) {
	}
}
