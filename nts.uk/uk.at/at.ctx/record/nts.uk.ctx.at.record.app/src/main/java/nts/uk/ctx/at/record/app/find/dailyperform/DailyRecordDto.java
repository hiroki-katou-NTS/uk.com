package nts.uk.ctx.at.record.app.find.dailyperform;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.app.find.dailyperform.goout.dto.OutingTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemOfDailyPerformDto;
import nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto.BreakTimeDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto.TemporaryTimeOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.AttendanceTimeByWorkOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

@Data
/** 日別実績（WORK） */
@AttendanceItemRoot(isContainer = true)
public class DailyRecordDto implements ConvertibleAttendanceItem {
	
	private String employeeId;
	
	private GeneralDate date;

	/** 勤務情報： 日別実績の勤務情報 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "日別実績の勤務情報")
	private WorkInformationOfDailyDto workInfo;

	/** 計算区分： 日別実績の計算区分 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "日別実績の計算区分")
	private CalcAttrOfDailyPerformanceDto calcAttr;

	/** 所属情報： 日別実績の所属情報 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "日別実績の所属情報")
	private AffiliationInforOfDailyPerforDto affiliationInfo;

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// TODO: list?
//	@AttendanceItemLayout(layout = "D", jpPropertyName = "社員の日別実績エラー一覧")
	private EmployeeDailyPerErrorDto errors;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "日別実績の外出時間帯", isOptional = true)
	private Optional<OutingTimeOfDailyPerformanceDto> outingTime = Optional.empty();

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "日別実績の休憩時間帯", listMaxLength = 2, enumField = "restTimeType", listNoIndex = true)
	private List<BreakTimeDailyDto> breakTime= new ArrayList<>();

	/** 勤怠時間: 日別実績の勤怠時間 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "日別実績の勤怠時間", isOptional = true)
	private Optional<AttendanceTimeDailyPerformDto> attendanceTime = Optional.empty();

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@AttendanceItemLayout(layout = "H", jpPropertyName = "日別実績の作業別勤怠時間", isOptional = true)
	private Optional<AttendanceTimeByWorkOfDailyDto> attendanceTimeByWork = Optional.empty();

	/** 出退勤: 日別実績の出退勤 */
	@AttendanceItemLayout(layout = "I", jpPropertyName = "日別実績の出退勤", isOptional = true)
	private Optional<TimeLeavingOfDailyPerformanceDto> timeLeaving = Optional.empty();

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@AttendanceItemLayout(layout = "J", jpPropertyName = "日別実績の短時間勤務時間帯", isOptional = true)
	private Optional<ShortTimeOfDailyDto> shortWorkTime = Optional.empty();

	/** 特定日区分: 日別実績の特定日区分 */
	@AttendanceItemLayout(layout = "K", jpPropertyName = "日別実績の特定日区分", isOptional = true)
	private Optional<SpecificDateAttrOfDailyPerforDto> specificDateAttr = Optional.empty();

	/** 入退門: 日別実績の入退門 */
	@AttendanceItemLayout(layout = "L", jpPropertyName = "日別実績の入退門", isOptional = true)
	private Optional<AttendanceLeavingGateOfDailyDto> attendanceLeavingGate = Optional.empty();

	/** 任意項目: 日別実績の任意項目 */
	@AttendanceItemLayout(layout = "M", jpPropertyName = "日別実績の任意項目", isOptional = true)
	private Optional<OptionalItemOfDailyPerformDto> optionalItem = Optional.empty();

	/** 編集状態: 日別実績の編集状態 */
	//@AttendanceItemLayout(layout = "N", jpPropertyName = "日別実績の編集状態", isList = true, listMaxLength = ?)
	private List<EditStateOfDailyPerformanceDto> editStates = new ArrayList<>();

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@AttendanceItemLayout(layout = "O", jpPropertyName = "日別実績の臨時出退勤", isOptional = true)
	private Optional<TemporaryTimeOfDailyPerformanceDto> temporaryTime = Optional.empty();
	
	public static DailyRecordDto builder(){
		return new DailyRecordDto();
	}
	
	public DailyRecordDto withWorkInfo(WorkInformationOfDailyDto workInfo){
		this.workInfo = workInfo;
		return this;
	}
	
	public DailyRecordDto withCalcAttr(CalcAttrOfDailyPerformanceDto calcAttr){
		this.calcAttr = calcAttr;
		return this;
	}
	
	public DailyRecordDto withAffiliationInfo(AffiliationInforOfDailyPerforDto affiliationInfo){
		this.affiliationInfo = affiliationInfo;
		return this;
	}
	
	public DailyRecordDto withErrors(EmployeeDailyPerErrorDto errors){
		this.errors = errors;
		return this;
	}
	
	public DailyRecordDto outingTime(OutingTimeOfDailyPerformanceDto outingTime){
		this.outingTime = Optional.ofNullable(outingTime);
		return this;
	}
	
	public DailyRecordDto addBreakTime(BreakTimeDailyDto breakTime){
		this.breakTime.add(breakTime);
		return this;
	}
	
	public DailyRecordDto addBreakTime(List<BreakTimeDailyDto> breakTime){
		this.breakTime.addAll(breakTime);
		return this;
	}
	
	public DailyRecordDto breakTime(List<BreakTimeDailyDto> breakTime){
		this.breakTime = breakTime == null ? new ArrayList<>() : breakTime;
		return this;
	}
	
	public DailyRecordDto attendanceTime(AttendanceTimeDailyPerformDto attendanceTime){
		this.attendanceTime = Optional.ofNullable(attendanceTime);
		return this;
	}
	
	public DailyRecordDto attendanceTimeByWork(AttendanceTimeByWorkOfDailyDto attendanceTimeByWork){
		this.attendanceTimeByWork = Optional.ofNullable(attendanceTimeByWork);
		return this;
	}
	
	public DailyRecordDto timeLeaving(TimeLeavingOfDailyPerformanceDto timeLeaving){
		this.timeLeaving = Optional.ofNullable(timeLeaving);
		return this;
	}
	
	public DailyRecordDto shortWorkTime(ShortTimeOfDailyDto shortWorkTime){
		this.shortWorkTime = Optional.ofNullable(shortWorkTime);
		return this;
	}
	
	public DailyRecordDto specificDateAttr(SpecificDateAttrOfDailyPerforDto specificDateAttr){
		this.specificDateAttr = Optional.ofNullable(specificDateAttr);
		return this;
	}
	
	public DailyRecordDto attendanceLeavingGate(AttendanceLeavingGateOfDailyDto attendanceLeavingGate){
		this.attendanceLeavingGate = Optional.ofNullable(attendanceLeavingGate);
		return this;
	}
	
	public DailyRecordDto optionalItems(OptionalItemOfDailyPerformDto optionalItem){
		this.optionalItem = Optional.ofNullable(optionalItem);
		return this;
	}
	
	public DailyRecordDto addEditStates(EditStateOfDailyPerformanceDto editStates){
		this.editStates.add(editStates);
		return this;
	}
	
	public DailyRecordDto addEditStates(List<EditStateOfDailyPerformanceDto> editStates){
		this.editStates.addAll(editStates);
		return this;
	}
	
	public DailyRecordDto editStates(List<EditStateOfDailyPerformanceDto> editStates){
		this.editStates = editStates == null ? new ArrayList<>() : editStates;
		return this;
	}
	
	public DailyRecordDto temporaryTime(TemporaryTimeOfDailyPerformanceDto temporaryTime){
		this.temporaryTime = Optional.ofNullable(temporaryTime);
		return this;
	}
	
	public DailyRecordDto workingDate(GeneralDate workingDate){
		this.date = workingDate;
		return this;
	}
	
	public DailyRecordDto employeeId(String employeeId){
		this.employeeId = employeeId;
		return this;
	}
	
	public DailyRecordDto complete(){
		return this;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}
}
