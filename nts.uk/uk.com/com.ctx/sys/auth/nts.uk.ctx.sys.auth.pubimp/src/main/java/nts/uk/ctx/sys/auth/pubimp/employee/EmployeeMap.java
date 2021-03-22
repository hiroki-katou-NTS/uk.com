package nts.uk.ctx.sys.auth.pubimp.employee;

import lombok.Data;

@Data
public class EmployeeMap {
	

	public String empID;
	public String wplID;

	public EmployeeMap(String empID, String wplID) {
		super();
		this.empID = empID;
		this.wplID = wplID;
	}
}
