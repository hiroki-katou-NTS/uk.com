package nts.uk.ctx.at.record.app.find.dailyperform.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetDto implements ItemConst {

	private Integer no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = START)
	private TimeStampDto start;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = END)
	private TimeStampDto end;

	private int breakTime;

	@Override
	public TimeSheetDto clone() {
		return new TimeSheetDto(no, start == null ? null : start.clone(), end == null ? null : end.clone(), breakTime);
	}
}
