package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の任意項目")
public class OptionalItemOfDailyPerformDto implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "任意項目値", isList = true)
	private List<OptionalItemValue> optionalItems;
}
