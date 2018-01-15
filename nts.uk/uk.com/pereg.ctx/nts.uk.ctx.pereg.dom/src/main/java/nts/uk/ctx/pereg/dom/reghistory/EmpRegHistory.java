package nts.uk.ctx.pereg.dom.reghistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpRegHistory extends AggregateRoot {

	protected String registeredEmployeeID;
	protected String companyId;
	protected GeneralDateTime registeredDate;
	protected String lastRegEmployeeID;
	protected String lastRegEmployeeCd;

	public static EmpRegHistory createFromJavaType(String registeredEmployeeID, String companyId,
			GeneralDateTime registeredDate, String lastRegEmployeeID, String lastRegEmployeeCd) {
		return new EmpRegHistory(registeredEmployeeID, companyId, registeredDate, lastRegEmployeeID, lastRegEmployeeCd);
	}

}
