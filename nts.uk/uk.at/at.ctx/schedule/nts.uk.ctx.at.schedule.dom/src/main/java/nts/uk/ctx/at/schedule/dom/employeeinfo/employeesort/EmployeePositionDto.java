/**
 * 
 */
package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePositionDto {
	
	/** 社員ID **/ 
	private String empID;
	
	private String jobtitleCode;
	
	public  Integer priority;

}
