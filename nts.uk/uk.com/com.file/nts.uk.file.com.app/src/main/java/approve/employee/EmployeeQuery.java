package approve.employee;

import lombok.Data;
import lombok.Value;

@Data
public class EmployeeQuery {
	
	private String employeeCode;
	private String employeeId;
	private String employeeName;
	private String workplaceCode;
	private String workplaceId;
	private String workplaceName;	


}
