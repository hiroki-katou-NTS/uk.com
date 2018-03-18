package nts.uk.ctx.at.record.dom.adapter.statusofemployee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecStatusOfEmployeeImport {

	private String employeeId;
	
	private GeneralDate refereneDate;
	
	private int statusOfEmployment;
	
	private int leaveHolidayType;
}
