package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.DurationFlg;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetirePlanCourseClass;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;


/**
 * @author thanhpv
 * 定年退職コース情報リスト
 */
@Getter
@AllArgsConstructor
public class RetirementCourseInformationDto {

	/** 雇用情報リスト．雇用コード */
	private String employmentCode;
	
	/** 雇用情報リスト．雇用名称 */
	private String employmentName;
	
	/** 定年退職コース区分 */
	private RetirePlanCourseClass retirePlanCourseClass;
	
	/** 定年年齢 */
	private RetirementAge retirementAge;
	
	/** 退職日条件 */
	private RetireDateTerm retireDateTerm;
	
	/** 定年退職コースID */
	private long retirePlanCourseId;
	
	/** 定年退職コースCD */
	private String retirePlanCourseCode;
	
	/** 定年退職コース名 */
	private String retirePlanCourseName;
	
	/** 継続区分 */
	private DurationFlg durationFlg;
}
