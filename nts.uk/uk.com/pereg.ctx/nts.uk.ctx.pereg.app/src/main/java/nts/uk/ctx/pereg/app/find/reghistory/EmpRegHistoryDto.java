package nts.uk.ctx.pereg.app.find.reghistory;

import lombok.Value;
import nts.uk.ctx.pereg.dom.reghistory.LastEmRegHistory;

@Value
public class EmpRegHistoryDto {

	String lastRegEmployeeID;

	String lastRegEmployeeOfCompanyID;

	public static EmpRegHistoryDto fromDomain(LastEmRegHistory domain) {
		return new EmpRegHistoryDto(domain.getLastRegEmployeeID(), domain.getLastRegEmployeeOfCompanyID());
	}

}
