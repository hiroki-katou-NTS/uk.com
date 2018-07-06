package nts.uk.ctx.at.record.app.command.monthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.monthly.affliation.AffiliationInfoOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.annualleave.AnnLeaRemNumEachMonthCommand;
import nts.uk.ctx.at.record.app.command.monthly.anyitem.AnyItemOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.attendancetime.AttendanceTimeOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.reserveleave.RsvLeaRemNumEachMonthCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public class MonthlyRecordWorkCommand extends MonthlyWorkCommonCommand {
	
	private final List<ItemValue> itemValues = new ArrayList<>();

	/** 月別実績の所属情報 */
	@Getter
	private final AffiliationInfoOfMonthlyCommand affiliationInfo = new AffiliationInfoOfMonthlyCommand();

	/** 月別実績の勤怠時間 */
	@Getter
	private final AttendanceTimeOfMonthlyCommand attendanceTime = new AttendanceTimeOfMonthlyCommand();

	/** 月別実績の任意項目 */
	@Getter
	private final AnyItemOfMonthlyCommand anyItem = new AnyItemOfMonthlyCommand();

	/** 年休月別残数データ */
	@Getter
	private final AnnLeaRemNumEachMonthCommand annualLeave = new AnnLeaRemNumEachMonthCommand();

	/** 積立年休月別残数データ */
	@Getter
	private final RsvLeaRemNumEachMonthCommand reserveLeave = new RsvLeaRemNumEachMonthCommand();



	public MonthlyWorkCommonCommand getCommand(String group){
		MonthlyWorkCommonCommand command = null;
		switch (group) {
		case MONTHLY_AFFILIATION_INFO_CODE:
			command = this.affiliationInfo;
			break;
		case MONTHLY_ATTENDANCE_TIME_CODE:
			command = this.attendanceTime;
			break;
		case MONTHLY_OPTIONAL_ITEM_CODE:
			command = this.anyItem;
			break;
		case MONTHLY_ANNUAL_LEAVING_REMAIN_CODE:
			command = this.annualLeave;
			break;
		case MONTHLY_RESERVE_LEAVING_REMAIN_CODE:
			command = this.reserveLeave;
			break;
		default:
			break;
		}
		return command;
	}
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		MonthlyRecordWorkDto fullDto = (MonthlyRecordWorkDto) item;
		this.affiliationInfo.setRecords(fullDto.getAffiliation());
		this.attendanceTime.setRecords(fullDto.getAttendanceTime());
		this.anyItem.setRecords(fullDto.getAnyItem());
		this.annualLeave.setRecords(fullDto.getAnnLeave());
		this.reserveLeave.setRecords(fullDto.getRsvLeave());
	}

	@Override
	public void forEmployee(String employeId) {
		super.employeeId(employeId);
		this.affiliationInfo.forEmployee(employeId);
		this.attendanceTime.forEmployee(employeId);
		this.anyItem.forEmployee(employeId);
		this.annualLeave.forEmployee(employeId);
		this.reserveLeave.forEmployee(employeId);
	}
	
	public MonthlyRecordWorkCommand fromItems(List<ItemValue> itemValues){
		this.itemValues.addAll(itemValues);
		return this;
	}
	
	public MonthlyRecordWorkCommand withData(MonthlyRecordWorkDto data){
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
		this.anyItem.yearMonth(yearMonth);
		this.annualLeave.yearMonth(yearMonth);
		this.reserveLeave.yearMonth(yearMonth);
	}

	@Override
	public void closureId(int closureId) {
		super.closureId(closureId);
		this.affiliationInfo.closureId(closureId);
		this.attendanceTime.closureId(closureId);
		this.anyItem.closureId(closureId);
		this.annualLeave.closureId(closureId);
		this.reserveLeave.closureId(closureId);
	}

	@Override
	public void closureDate(ClosureDateDto closureDate) {
		super.closureDate(closureDate);
		this.affiliationInfo.closureDate(closureDate);
		this.attendanceTime.closureDate(closureDate);
		this.anyItem.closureDate(closureDate);
		this.annualLeave.closureDate(closureDate);
		this.reserveLeave.closureDate(closureDate);
	}
}
