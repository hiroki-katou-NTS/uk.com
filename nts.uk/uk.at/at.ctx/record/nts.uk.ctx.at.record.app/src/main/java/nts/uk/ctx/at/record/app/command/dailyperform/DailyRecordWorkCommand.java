package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.BusinessTypeOfDailyPerformCommand;
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
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

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
	
	/** 所属情報： 日別実績の所属情報 */
	@Getter
	private final BusinessTypeOfDailyPerformCommand businessType = new BusinessTypeOfDailyPerformCommand();

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

	public DailyWorkCommonCommand getCommand(String group) {
		DailyWorkCommonCommand command = null;
		switch (group) {
		case "A":
			command = this.workInfo;
			break;
		case "B":
			command = this.calcAttr;
			break;
		case "C":
			command = this.affiliationInfo;
			break;
		case "D":
			command = this.businessType;
			break;
		case "E":
			command = this.outingTime;
			break;
		case "F":
			command = this.breakTime;
			break;
		case "G":
			command = this.attendanceTime;
			break;
		case "H":
			command = this.attendanceTimeByWork;
			break;
		case "I":
			command = this.timeLeaving;
			break;
		case "J":
			command = this.shortWorkTime;
			break;
		case "K":
			command = this.specificDateAttr;
			break;
		case "L":
			command = this.attendanceLeavingGate;
			break;
		case "M":
			command = this.optionalItem;
			break;
		case "N":
			command = this.editState;
			break;
		case "O":
			command = this.temporaryTime;
			break;
		case "P":
			command = this.pcLogInfo;
			break;
		case "Q":
			command = this.remarks;
			break;
		default:
			break;
		}
		return command;
	}

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		DailyRecordDto fullDto = (DailyRecordDto) item;
		this.workInfo.setRecords(fullDto.getWorkInfo());
		this.calcAttr.setRecords(fullDto.getCalcAttr());
		this.affiliationInfo.setRecords(fullDto.getAffiliationInfo());
		this.errors.setRecords(fullDto.getErrors());
		this.outingTime.setRecords(fullDto.getOutingTime().orElse(null));
		this.businessType.setRecords(fullDto.getBusinessType().orElse(null));
		fullDto.getBreakTime().stream().forEach(c -> this.breakTime.setRecords(c));
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
	}

	@Override
	public void forEmployee(String employeId) {
		super.forEmployee(employeId);
		this.attendanceTime.forEmployee(employeId);
		this.workInfo.forEmployee(employeId);
		this.calcAttr.forEmployee(employeId);
		this.affiliationInfo.forEmployee(employeId);
		this.businessType.forEmployee(employeId);
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
		this.businessType.withDate(date);
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
				.breakTime(breakTime.getData())
				.editStates(editState.getData())
				.attendanceLeavingGate(attendanceLeavingGate.getData().orElse(null))
				.attendanceTime(attendanceTime.getData().orElse(null))
				.attendanceTimeByWork(attendanceTimeByWork.getData().orElse(null))
				.employeeId(getEmployeeId())
				.optionalItems(optionalItem.getData().orElse(null))
				.outingTime(outingTime.getData().orElse(null))
				.pcLogInfo(pcLogInfo.getData().orElse(null))
				.shortWorkTime(shortWorkTime.getData().orElse(null))
				.specificDateAttr(specificDateAttr.getData().orElse(null))
				.temporaryTime(temporaryTime.getData().orElse(null))
				.timeLeaving(timeLeaving.getData().orElse(null))
				.withAffiliationInfo(affiliationInfo.getData())
				.withCalcAttr(calcAttr.getData())
				.withErrors(errors.getData())
				.withWorkInfo(workInfo.getData())
				.workingDate(getWorkDate())
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

	public DailyRecordWorkCommand fromItems(List<ItemValue> itemValues) {
		this.itemValues.addAll(itemValues);
		return this;
	}

	public DailyRecordWorkCommand withData(DailyRecordDto data) {
		this.setRecords(data);
		return this;
	}

	public List<ItemValue> itemValues() {
		return new ArrayList<>(this.itemValues);
	}

	@Override
	public void updateData(Object data) {
	}
}
