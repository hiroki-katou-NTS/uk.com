package nts.uk.ctx.pereg.app.find.reginfo.reghistory;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;

@Value
public class EmpRegHistoryDto {

	String registeredEmployeeID;

	String lastRegEmployeeID;

	public static EmpRegHistoryDto fromDomain(EmpRegHistory domain) {
		return new EmpRegHistoryDto(domain.getRegisteredEmployeeID(), domain.getLastRegEmployeeID());
	}

}
