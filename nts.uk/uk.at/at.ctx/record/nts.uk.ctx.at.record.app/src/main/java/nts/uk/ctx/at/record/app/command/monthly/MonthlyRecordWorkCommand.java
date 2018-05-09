package nts.uk.ctx.at.record.app.command.monthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.monthly.affliation.AffiliationInfoOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.attendancetime.AttendanceTimeOfMonthlyCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public class MonthlyRecordWorkCommand extends MonthlyWorkCommonCommand {
	
	private final List<ItemValue> itemValues = new ArrayList<>();

	/** 月別実績の所属情報 */
	@Getter
	private final AffiliationInfoOfMonthlyCommand affiliationInfo = new AffiliationInfoOfMonthlyCommand();

	/** 月別実績の勤怠時間 */
	@Getter
	private final AttendanceTimeOfMonthlyCommand attendanceTime = new AttendanceTimeOfMonthlyCommand();


	public MonthlyWorkCommonCommand getCommand(String group){
		MonthlyWorkCommonCommand command = null;
		switch (group) {
		case "A":
			command = this.affiliationInfo;
			break;
		case "B":
			command = this.attendanceTime;
			break;
		default:
			break;
		}
		return command;
	}
	
	@Override
	public void setRecords(AttendanceItemCommon item) {
		MonthlyRecordWorkDto fullDto = (MonthlyRecordWorkDto) item;
		this.affiliationInfo.setRecords(fullDto.getAffiliation());
		this.attendanceTime.setRecords(fullDto.getAttendanceTime());
	}

	@Override
	public void forEmployee(String employeId) {
		super.employeeId(employeId);
		this.affiliationInfo.forEmployee(employeId);
		this.attendanceTime.forEmployee(employeId);
	}
	
	public MonthlyRecordWorkCommand fromItems(List<ItemValue> itemValues){
		this.itemValues.addAll(itemValues);
		return this;
	}
	
	public MonthlyRecordWorkCommand withData(DailyRecordDto data){
		this.setRecords(data);
		return this;
	}
	
	public List<ItemValue> itemValues(){
		return new ArrayList<>(this.itemValues);
	}

	@Override
	public void updateData(Object data) {
	}

	@Override
	public void yearMonth(YearMonth yearMonth) {
		super.yearMonth(yearMonth);
		this.affiliationInfo.yearMonth(yearMonth);
		this.attendanceTime.yearMonth(yearMonth);
	}

	@Override
	public void closureId(int closureId) {
		super.closureId(closureId);
		this.affiliationInfo.closureId(closureId);
		this.attendanceTime.closureId(closureId);
	}

	@Override
	public void closureDate(ClosureDateDto closureDate) {
		super.closureDate(closureDate);
		this.affiliationInfo.closureDate(closureDate);
		this.attendanceTime.closureDate(closureDate);
	}
}
