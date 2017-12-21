package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の特定日区分")
public class SpecificDateAttrOfDailyPerforDto implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定日区分")
	private List<SpecificDateAttrDto> sepecificDateAttrs;
}
