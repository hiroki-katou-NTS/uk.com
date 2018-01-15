package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.erroralarm.EmployeeDailyPerErrorCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

public class DailyRecordWorkCommand extends DailyWorkCommonCommand {
	
	private final List<ItemValue> itemValues = new ArrayList<>();

	/** 勤務情報： 日別実績の勤務情報 */
	@Getter
	private final WorkInformationOfDailyPerformCommand workInfoCommand = new WorkInformationOfDailyPerformCommand();

	/** 計算区分： 日別実績の計算区分 */
	@Getter
	private final CalcAttrOfDailyPerformanceCommand calcAttrCommand = new CalcAttrOfDailyPerformanceCommand();

	/** 所属情報： 日別実績の所属情報 */
	@Getter
	private final AffiliationInforOfDailyPerformCommand affiliationInfoCommand = new AffiliationInforOfDailyPerformCommand();

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// TODO: list?
	@Getter
	private final EmployeeDailyPerErrorCommand errorCommand = new EmployeeDailyPerErrorCommand();

	/** 外出時間帯: 日別実績の外出時間帯 */
	@Getter
	private final OutingTimeOfDailyPerformanceCommand outingTimeCommand = new OutingTimeOfDailyPerformanceCommand();

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@Getter
	private final BreakTimeOfDailyPerformanceCommand breakTimeCommand = new BreakTimeOfDailyPerformanceCommand();

	/** 勤怠時間: 日別実績の勤怠時間 */
	@Getter
	private final AttendanceTimeOfDailyPerformCommand attendanceTimeCommand = new AttendanceTimeOfDailyPerformCommand();

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@Getter
	private final AttendanceTimeByWorkOfDailyCommand attendanceTimeByWorkCommand = new AttendanceTimeByWorkOfDailyCommand();

	/** 出退勤: 日別実績の出退勤 */
	@Getter
	private final TimeLeavingOfDailyPerformanceCommand timeLeavingCommand = new TimeLeavingOfDailyPerformanceCommand();

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@Getter
	private final ShortTimeOfDailyCommand shortWorkTimeCommand = new ShortTimeOfDailyCommand();

	/** 特定日区分: 日別実績の特定日区分 */
	@Getter
	private final SpecificDateAttrOfDailyCommand specificDateAttrCommand = new SpecificDateAttrOfDailyCommand();

	/** 入退門: 日別実績の入退門 */
	@Getter
	private final AttendanceLeavingGateOfDailyCommand attendanceLeavingGateCommand = new AttendanceLeavingGateOfDailyCommand();

	/** 任意項目: 日別実績の任意項目 */
	@Getter
	private final OptionalItemOfDailyPerformCommand optionalItemCommand = new OptionalItemOfDailyPerformCommand();

	/** 編集状態: 日別実績の編集状態 */
	@Getter
	private final EditStateOfDailyPerformCommand editStateCommand = new EditStateOfDailyPerformCommand();

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@Getter
	private final TemporaryTimeOfDailyPerformanceCommand temporaryTimeCommand = new TemporaryTimeOfDailyPerformanceCommand();

	public DailyWorkCommonCommand getCommand(String group){
		DailyWorkCommonCommand command = null;
		switch (group) {
		case "A":
			command = this.workInfoCommand;
			break;
		case "B":
			command = this.calcAttrCommand;
			break;
		case "C":
			command = this.affiliationInfoCommand;
			break;
		case "D":
			command = this.errorCommand;
			break;
		case "E":
			command = this.outingTimeCommand;
			break;
		case "F":
			command = this.breakTimeCommand;
			break;
		case "G":
			command = this.attendanceTimeCommand;
			break;
		case "H":
			command = this.attendanceTimeByWorkCommand;
			break;
		case "I":
			command = this.timeLeavingCommand;
			break;
		case "J":
			command = this.shortWorkTimeCommand;
			break;
		case "K":
			command = this.specificDateAttrCommand;
			break;
		case "L":
			command = this.attendanceLeavingGateCommand;
			break;
		case "M":
			command = this.optionalItemCommand;
			break;
		case "N":
			command = this.editStateCommand;
			break;
		case "O":
			command = this.temporaryTimeCommand;
			break;
		default:
			break;
		}
		return command;
	}
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		DailyRecordDto fullDto = (DailyRecordDto) item;
		this.attendanceTimeCommand.setRecords(fullDto.getAttendanceTime().orElse(null));
		this.workInfoCommand.setRecords(fullDto.getWorkInfo());
		this.calcAttrCommand.setRecords(fullDto.getCalcAttr());
		this.affiliationInfoCommand.setRecords(fullDto.getAffiliationInfo());
		this.errorCommand.setRecords(fullDto.getErrors());
		this.outingTimeCommand.setRecords(fullDto.getOutingTime().orElse(null));
		fullDto.getBreakTime().stream().forEach(c -> this.breakTimeCommand.setRecords(c));
		this.attendanceTimeByWorkCommand.setRecords(fullDto.getAttendanceTimeByWork().orElse(null));
		this.timeLeavingCommand.setRecords(fullDto.getTimeLeaving().orElse(null));
		this.shortWorkTimeCommand.setRecords(fullDto.getShortWorkTime().orElse(null));
		this.specificDateAttrCommand.setRecords(fullDto.getSpecificDateAttr().orElse(null));
		this.attendanceLeavingGateCommand.setRecords(fullDto.getAttendanceLeavingGate().orElse(null));
		this.optionalItemCommand.setRecords(fullDto.getOptionalItem().orElse(null));
		fullDto.getEditStates().stream().forEach(c -> this.editStateCommand.setRecords(c));
		this.temporaryTimeCommand.setRecords(fullDto.getTemporaryTime().orElse(null));
	}

	@Override
	public void forEmployee(String employeId) {
		super.forEmployee(employeId);
		this.attendanceTimeCommand.forEmployee(employeId);
		this.workInfoCommand.forEmployee(employeId);
		this.calcAttrCommand.forEmployee(employeId);
		this.affiliationInfoCommand.forEmployee(employeId);
		this.errorCommand.forEmployee(employeId);
		this.outingTimeCommand.forEmployee(employeId);
		this.breakTimeCommand.forEmployee(employeId);
		this.attendanceTimeByWorkCommand.forEmployee(employeId);
		this.timeLeavingCommand.forEmployee(employeId);
		this.shortWorkTimeCommand.forEmployee(employeId);
		this.specificDateAttrCommand.forEmployee(employeId);
		this.attendanceLeavingGateCommand.forEmployee(employeId);
		this.optionalItemCommand.forEmployee(employeId);
		this.editStateCommand.forEmployee(employeId);
		this.temporaryTimeCommand.forEmployee(employeId);
	}

	@Override
	public void withDate(GeneralDate date) {
		super.withDate(date);
		this.attendanceTimeCommand.withDate(date);
		this.workInfoCommand.withDate(date);
		this.calcAttrCommand.withDate(date);
		this.affiliationInfoCommand.withDate(date);
		this.errorCommand.withDate(date);
		this.outingTimeCommand.withDate(date);
		this.breakTimeCommand.withDate(date);
		this.attendanceTimeByWorkCommand.withDate(date);
		this.timeLeavingCommand.withDate(date);
		this.shortWorkTimeCommand.withDate(date);
		this.specificDateAttrCommand.withDate(date);
		this.attendanceLeavingGateCommand.withDate(date);
		this.optionalItemCommand.withDate(date);
		this.editStateCommand.withDate(date);
		this.temporaryTimeCommand.withDate(date);
	}
	
	public static DailyRecordWorkCommand open(){
		return new DailyRecordWorkCommand();
	}
			
	public DailyRecordWorkCommand forEmployeeId(String employeeId){
		this.forEmployee(employeeId);
		return this;
	}
	
	public DailyRecordWorkCommand withWokingDate(GeneralDate workDate){
		this.withDate(workDate);
		return this;
	}
	
	public DailyRecordWorkCommand fromItems(List<ItemValue> itemValues){
		this.itemValues.addAll(itemValues);
		return this;
	}
	
	public DailyRecordWorkCommand withData(DailyRecordDto data){
		this.setRecords(data);
		return this;
	}
	
	public List<ItemValue> itemValues(){
		return new ArrayList<>(this.itemValues);
	}

	@Override
	public Object toDomain() {
		return null;
	}
}
