package find.person.setting.regHistory;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.regisHistory.EmpRegHistory;

@Value
public class EmpRegHistoryDto {

	String registeredEmployeeID;

	String lastRegEmployeeID;

	public static EmpRegHistoryDto fromDomain(EmpRegHistory domain) {
		return new EmpRegHistoryDto(domain.getRegisteredEmployeeID(), domain.getLastRegEmployeeID());
	}

}
