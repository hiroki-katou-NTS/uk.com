package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetirementCourseDto {
	// 雇用コード.
	private String employmentCode;
	// 雇用名称.
	private String employmentName;
	// 定年退職コース区分
	private Integer retirePlanCourseClass;
	// 定年年齢
	private Integer retirementAge;
	//退職日基準
	private String retireDateBase; 
	// 退職日条件
	private RetireDateTermDto retireDateTerm;
	// 定年退職コースID
	private long retirePlanCourseId;
	// 定年退職コースCD
	private String retirePlanCourseCode;	
	// 定年退職コース名
	private String retirePlanCourseName;
	// 継続区分
	private Integer durationFlg;
}
