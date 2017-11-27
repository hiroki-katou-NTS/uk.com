package nts.uk.ctx.pereg.app.find.reghistory;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.LastEmRegHistory;

@Value
public class EmpRegHistoryDto {

	String lastRegEmployeeID;

	String lastRegEmployeeOfCompanyID;

	public static EmpRegHistoryDto fromDomain(LastEmRegHistory domain) {
		return new EmpRegHistoryDto(domain.getLastRegEmployeeID(), domain.getLastRegEmployeeOfCompanyID());
	}

}
