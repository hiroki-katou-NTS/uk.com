package nts.uk.ctx.hr.shared.dom.dateTerm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class EmployeeDateDto {

	private String employeeId;
	
	private GeneralDate targetDate;
}
