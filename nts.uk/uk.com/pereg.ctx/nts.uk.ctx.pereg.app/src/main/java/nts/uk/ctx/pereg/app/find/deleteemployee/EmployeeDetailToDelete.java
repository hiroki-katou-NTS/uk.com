package nts.uk.ctx.pereg.app.find.deleteemployee;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class EmployeeDetailToDelete {

	private GeneralDate datedelete;

	private String reason;

	public static EmployeeDetailToDelete fromDomain(Object[] obj) {
		return new EmployeeDetailToDelete(GeneralDate.legacyDate(new Date(obj[0].toString())), obj[1].toString());
	}
	
}
