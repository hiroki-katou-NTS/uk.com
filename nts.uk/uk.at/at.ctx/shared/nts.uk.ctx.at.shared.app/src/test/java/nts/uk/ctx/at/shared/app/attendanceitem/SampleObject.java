package nts.uk.ctx.at.shared.app.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleObject {

	@AttendanceItemValue()
	@AttendanceItemLayout(layout = "A", jpPropertyName = "A")
	private String attendanceItem;

	@AttendanceItemValue()
	@AttendanceItemLayout(layout = "B", jpPropertyName = "AffiliationInfor")
	private String attendanceItem2;
}
