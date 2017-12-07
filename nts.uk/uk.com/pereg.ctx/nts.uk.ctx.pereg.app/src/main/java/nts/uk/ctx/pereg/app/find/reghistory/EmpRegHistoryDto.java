package nts.uk.ctx.pereg.app.find.reghistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.reghistory.LastEmRegHistory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpRegHistoryDto {

	RegEmployeeDto lastRegEmployee;

	RegEmployeeDto lastRegEmployeeOfCompany;

	public void fromDomain(LastEmRegHistory domain) {

		this.lastRegEmployee.employeeID = domain.getLastRegEmployeeID();

		this.lastRegEmployeeOfCompany.employeeID = domain.getLastRegEmployeeOfCompanyID();
	}

}
