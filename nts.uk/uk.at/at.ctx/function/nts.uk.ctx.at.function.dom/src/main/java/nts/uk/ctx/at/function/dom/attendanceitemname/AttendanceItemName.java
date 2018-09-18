package nts.uk.ctx.at.function.dom.attendanceitemname;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceItemName {

	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;

	private int userCanUpdateAtr;

	private int nameLineFeedPosition;

	private int typeOfAttendanceItem;

	private int frameCategory;

}
