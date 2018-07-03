package nts.uk.file.at.app.export.attendancerecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Employee.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

	/** The employee id. */
	String employeeId;

	/** The employee name. */
	String employeeName;
	
	/** The employee code. */
	String employeeCode;
	
	/** The workplace code. */
	String workplaceCode;
	
	/** The workplace id. */
	String workplaceId;

}
