package nts.uk.ctx.at.record.app.find.dailyperform.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

@Data
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetDto implements ItemConst, AttendanceItemDataGate {

	private int no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = START)
	private TimeStampDto start;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = END)
	private TimeStampDto end;

	private int breakTime;

	@Override
	public TimeSheetDto clone() {
		return new TimeSheetDto(no, start == null ? null : start.clone(), end == null ? null : end.clone(), breakTime);
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case START:
			return Optional.ofNullable(start);
		case END:
			return Optional.ofNullable(end);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case START:
			start = (TimeStampDto) value;
			break;
		case END:
			end = (TimeStampDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case START:
		case END:
			return new TimeStampDto();
		default:
			return null;
		}
	}
}
