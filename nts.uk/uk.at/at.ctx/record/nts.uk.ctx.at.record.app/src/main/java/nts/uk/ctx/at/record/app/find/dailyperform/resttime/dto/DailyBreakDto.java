package nts.uk.ctx.at.record.app.find.dailyperform.resttime.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class DailyBreakDto implements ItemConst {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME_ZONE, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private List<TimeSheetDto> timeZone;

	/** 休憩種類 */
	private int attr;

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
}
