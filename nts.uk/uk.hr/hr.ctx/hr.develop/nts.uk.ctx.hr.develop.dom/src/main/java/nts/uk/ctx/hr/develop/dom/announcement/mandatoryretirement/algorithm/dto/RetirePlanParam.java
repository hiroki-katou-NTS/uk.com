package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Builder
public class RetirePlanParam {
	// 社員ID
	private String employeeId;
	// 雇用コード.
	private String employmentCode;
	// 誕生日
	private GeneralDate birthday;
	// 定年年齢
	private Integer retirementAge;
}
