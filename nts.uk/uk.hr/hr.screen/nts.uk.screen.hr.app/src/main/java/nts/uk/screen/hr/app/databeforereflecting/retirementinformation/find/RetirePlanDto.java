package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder

public class RetirePlanDto {
	// 社員ID
	private String employeeId;
	// 雇用コード.
	private String employmentCode;

	private GeneralDate birthday;
	// 定年年齢
	private Integer retirementAge;
}
