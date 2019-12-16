package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class RetirementDateDto {
	// 社員ID
	private String employeeId;
	// 雇用コード.
	private String employmentCode;
	//退職日
	private GeneralDate retirementDate;
}
