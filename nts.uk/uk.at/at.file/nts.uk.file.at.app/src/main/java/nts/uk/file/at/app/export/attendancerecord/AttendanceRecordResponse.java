package nts.uk.file.at.app.export.attendancerecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class AttendanceRecordRespond.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordResponse {

	/** The employee id. */
	String employeeId;

	/** The employee name. */
	String employeeName;

	/** The date. */
	GeneralDate date;

	/** The attendance item name. */
	String attendanceItemName;

	/** The value. */
	String value;

	/** The out frame attribute */
	int outFrameAttribute;
}
