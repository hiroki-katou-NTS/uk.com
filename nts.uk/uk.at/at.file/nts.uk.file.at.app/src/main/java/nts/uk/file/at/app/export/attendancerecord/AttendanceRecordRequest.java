package nts.uk.file.at.app.export.attendancerecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class AttendanceRecordRequest.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordRequest {

	/** The employee code list. */
	private List<Employee> employeeList;

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/** The code */
	private String layout;
	
	/** The file type. */
	private int mode;
	
	/** The closure id. */
	private int closureId;
	
	/** The condition. */
	private AttendanceRecordOutputConditionsDto condition;
	
	
}
