package nts.uk.ctx.pereg.dom.reghistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastEmRegHistory extends EmpRegHistory {
	String lastRegEmployeeOfCompanyID;
	String lastRegEmployeeOfCompanyCd;

	public static LastEmRegHistory createFromJavaType(String registeredEmployeeID, String companyId,
			GeneralDateTime registeredDate, String lastRegEmployeeID, String lastRegEmployeeCd,
			String lastRegEmployeeOfCompanyID, String lastRegEmployeeOfCompanyCd) {
		return new LastEmRegHistory(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID,
				lastRegEmployeeCd, lastRegEmployeeOfCompanyID, lastRegEmployeeOfCompanyCd);

	}

	public LastEmRegHistory(String registeredEmployeeID, String companyId, GeneralDateTime registeredDate,
			String lastRegEmployeeID, String lastRegEmployeeCd, String lastRegEmployeeOfCompanyID,
			String lastRegEmployeeOfCompanyCd) {
		super(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID, lastRegEmployeeCd);
		this.lastRegEmployeeOfCompanyID = lastRegEmployeeOfCompanyID;
		this.lastRegEmployeeOfCompanyCd = lastRegEmployeeOfCompanyCd;
	}

}
