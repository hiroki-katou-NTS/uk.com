package nts.uk.ctx.pereg.dom.person.info.setting.reghistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpRegHistory extends AggregateRoot {

	protected String registeredEmployeeID;
	protected String companyId;
	protected GeneralDate registeredDate;
	protected String lastRegEmployeeID;

	public static EmpRegHistory createFromJavaType(String registeredEmployeeID, String companyId,
			GeneralDate registeredDate, String lastRegEmployeeID) {
		return new EmpRegHistory(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID);
	}

}
