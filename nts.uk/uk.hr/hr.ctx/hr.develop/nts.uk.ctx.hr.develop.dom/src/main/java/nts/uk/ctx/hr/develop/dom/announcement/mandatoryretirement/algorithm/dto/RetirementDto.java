package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;

@AllArgsConstructor
@Getter
public class RetirementDto {

	private String employeeId;

	private String employeeCode;
	
	private GeneralDate birthday;
	
	private RetirementAge retirementAge;
}
