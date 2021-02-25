package nts.uk.ctx.at.shared.app.attendanceitem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

@Getter
@Setter
@AttendanceItemRoot(rootName = "")
public class SampleConvertibleAttendanceItem implements ConvertibleAttendanceItem {

	@AttendanceItemLayout(layout = "A", jpPropertyName = "A")
	private SampleObject attendanceItem;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "B", listMaxLength = 2)
	private List<SampleConvertibleAttendanceItem> attendanceItems;

	@AttendanceItemValue
	@AttendanceItemLayout(layout = "C", jpPropertyName = "C")
	private String attendanceItemB;

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
	public Object toDomain(String emp, GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}
}
