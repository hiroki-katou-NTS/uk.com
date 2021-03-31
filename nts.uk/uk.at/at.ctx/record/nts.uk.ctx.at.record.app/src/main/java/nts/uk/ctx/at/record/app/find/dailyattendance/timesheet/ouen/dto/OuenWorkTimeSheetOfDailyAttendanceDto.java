/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

/**
 *
 */
@AttendanceItemRoot(rootName = ItemConst.DAILY_SUPPORT_TIMESHEET_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OuenWorkTimeSheetOfDailyAttendanceDto extends AttendanceItemCommon{
	
	private static final long serialVersionUID = 1L;
	
	/** 応援勤務枠No: 応援勤務枠No */
	private int no;
	
	/** 作業内容: 作業内容 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORK_CONTENT)
	private WorkContentDto workContent;
	
	/** 時間帯: 時間帯別勤怠の時間帯 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TIME_ZONE)
	private TimeSheetOfAttendanceEachOuenSheetDto timeSheet;
	
	public static OuenWorkTimeSheetOfDailyAttendanceDto getDto(OuenWorkTimeSheetOfDailyAttendance domain) {
		OuenWorkTimeSheetOfDailyAttendanceDto result = new OuenWorkTimeSheetOfDailyAttendanceDto();
		if (domain != null)
			return null;
		
		return result;
	}
	
	@Override
	public String rootName() { return DAILY_SUPPORT_TIMESHEET_NAME; }
	
	@Override
	public boolean isRoot() { return true; }
	
	@Override
	public int size(String path) {
		return 20;
	}

	@Override
	public OuenWorkTimeSheetOfDailyAttendanceDto clone() {
		OuenWorkTimeSheetOfDailyAttendanceDto result = new OuenWorkTimeSheetOfDailyAttendanceDto();
		result.setNo(no);
		result.setWorkContent(workContent == null ? null : workContent.clone());
		result.setTimeSheet(timeSheet == null ? null : timeSheet.clone());
		return result;
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case WORK_CONTENT:
			return Optional.ofNullable(workContent);
		case TIME_ZONE:
			return Optional.ofNullable(timeSheet);
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case WORK_CONTENT:
			workContent = (WorkContentDto) value;
			break;
		case TIME_ZONE:
			timeSheet = (TimeSheetOfAttendanceEachOuenSheetDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case WORK_CONTENT:
			return new WorkContentDto();
		case TIME_ZONE:
			return new TimeSheetOfAttendanceEachOuenSheetDto();
		default:
			return null;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(WORK_CONTENT) || path.equals(TIME_ZONE)) {
			return PropType.OBJECT;
		}
		return super.typeOf(path);
	}
	
	@Override
	public String employeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDate workingDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toDomain(String employeeId, GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}
}
