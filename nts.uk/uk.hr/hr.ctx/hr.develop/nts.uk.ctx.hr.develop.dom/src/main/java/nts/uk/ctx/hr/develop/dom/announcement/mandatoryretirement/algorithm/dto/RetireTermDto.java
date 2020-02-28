package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetirePlanCourseClass;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;

@Getter
@AllArgsConstructor
public class RetireTermDto {

	/** 雇用情報リスト．雇用コード */
	private String employmentCode;
	
	/** 定年年齢 */
	private RetirementAge retirementAge;
	
	/** 定年退職コース区分 */
	private RetirePlanCourseClass retirePlanCourseClass;
}
