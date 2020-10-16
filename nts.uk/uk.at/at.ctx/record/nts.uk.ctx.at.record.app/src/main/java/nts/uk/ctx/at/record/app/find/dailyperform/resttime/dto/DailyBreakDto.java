package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class DailyBreakDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_ZONE, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private List<TimeSheetDto> timeZone;

	/** 休憩種類 */
	private int attr;

	@Override
	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_WORK_REF;
		case 1:
			return E_SCHEDULE_REF;
		default:
			return EMPTY_STRING;
		}
	}
	
	public DailyBreakDto clone(){
		return new DailyBreakDto(timeZone.stream().map(c -> c.clone()).collect(Collectors.toList()), attr);
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return new TimeSheetDto();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (path.equals(TIME_ZONE)) {
			return (List<T>) this.timeZone;
		}
		
		return new ArrayList<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		
		if (path.equals(TIME_ZONE)) {
			this.timeZone = (List<TimeSheetDto>) value;
		}
	}
	
	@Override
	public int size(String path) {
		if (path.equals(TIME_ZONE)) {
			return 10;
		}
		return 0;
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_WORK_REF:
			this.attr = 0;
			break;
		case E_SCHEDULE_REF:
			this.attr = 1;
			break;
		default:
		}
	}

	@Override
	public PropType typeOf(String path) {
		if (path.equals(TIME_ZONE)) {
			return PropType.IDX_IN_ENUM;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
	
//	@Override
//	public boolean enumNeedSet() {
//		return true;
//	}
}
