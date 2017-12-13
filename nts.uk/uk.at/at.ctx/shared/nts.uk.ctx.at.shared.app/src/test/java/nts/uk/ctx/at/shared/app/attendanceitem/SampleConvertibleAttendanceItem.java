package nts.uk.ctx.at.shared.app.attendanceitem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Getter
@Setter
public class SampleConvertibleAttendanceItem implements ConvertibleAttendanceItem{

	@AttendanceItemLayout(layout="A")
	private SampleObject attendanceItem;
	
	@AttendanceItemLayout(layout="B", isList=true)
	private List<SampleConvertibleAttendanceItem> attendanceItems;
	
	@AttendanceItemValue(type=ValueType.INTEGER, itemId=2)
	@AttendanceItemLayout(layout="C")
	private String attendanceItemB;
}
