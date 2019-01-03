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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}

	

	
	
}
