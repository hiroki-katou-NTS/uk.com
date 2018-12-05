package nts.uk.ctx.pereg.app.find.deleteemployee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class EmployeeDetailToDelete {

	private GeneralDate datedelete;

	private String reason;

	public static EmployeeDetailToDelete fromDomain(Object[] obj) {
		return new EmployeeDetailToDelete(GeneralDate.fromString(obj[0].toString(), "yyyy/MM/dd"), obj[1].toString());
	}
}
