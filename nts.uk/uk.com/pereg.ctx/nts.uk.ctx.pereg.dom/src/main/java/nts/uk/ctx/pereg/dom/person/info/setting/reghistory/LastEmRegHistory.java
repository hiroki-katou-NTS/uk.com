package nts.uk.ctx.pereg.dom.person.info.setting.reghistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastEmRegHistory extends EmpRegHistory {
	String lastRegEmployeeOfCompanyID;

	public static LastEmRegHistory createFromJavaType(String registeredEmployeeID, String companyId,
			GeneralDate registeredDate, String lastRegEmployeeID, String lastRegEmployeeOfCompanyID) {
		return new LastEmRegHistory(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID,
				lastRegEmployeeOfCompanyID);

	}

	public LastEmRegHistory(String registeredEmployeeID, String companyId, GeneralDate registeredDate,
			String lastRegEmployeeID, String lastRegEmployeeOfCompanyID) {
		super(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID);
		this.lastRegEmployeeOfCompanyID = lastRegEmployeeOfCompanyID;
	}

}
