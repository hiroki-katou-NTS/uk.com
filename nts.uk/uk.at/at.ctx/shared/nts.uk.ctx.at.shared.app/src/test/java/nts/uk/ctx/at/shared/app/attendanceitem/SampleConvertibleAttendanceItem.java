package nts.uk.ctx.at.shared.app.attendanceitem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Getter
@Setter
@AttendanceItemRoot(rootName = "")
public class SampleConvertibleAttendanceItem implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "A")
	private SampleObject attendanceItem;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "B", listMaxLength = 2)
	private List<SampleConvertibleAttendanceItem> attendanceItems;

	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(layout = "C", jpPropertyName = "C")
	private String attendanceItemB;
}
