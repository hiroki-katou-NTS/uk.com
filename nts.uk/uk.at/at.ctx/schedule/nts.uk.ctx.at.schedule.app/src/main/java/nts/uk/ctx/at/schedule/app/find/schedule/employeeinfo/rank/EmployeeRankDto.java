package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank;

import lombok.Data;

@Data
public class EmployeeRankDto {

	public String employeeID;
	
	public String emplRankCode;
		
	public EmployeeRankDto(String employeeID, String emplRankCode) {
		this.employeeID = employeeID;
		this.emplRankCode = emplRankCode;
	}

}
