package nts.uk.ctx.at.shared.app.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleObject {

	@AttendanceItemValue(itemId=1)
	@AttendanceItemLayout(layout="A")
	private String attendanceItem;
	
	@AttendanceItemValue(itemId=3)
	@AttendanceItemLayout(layout="B")
	private String attendanceItem2;
}
