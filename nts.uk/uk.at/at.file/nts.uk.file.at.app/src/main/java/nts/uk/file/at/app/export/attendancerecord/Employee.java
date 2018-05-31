package nts.uk.file.at.app.export.attendancerecord;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * The Class Employee.
 */
@Value
@Getter
@Setter
public class Employee {
	
	/** The employee id. */
	String employeeId;
	
	/** The employee name. */
	String employeeName;

}
