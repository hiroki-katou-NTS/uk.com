package nts.uk.ctx.hr.develop.app.jmm018retire;

import lombok.Data;

@Data
public class RetirePlanCourceDto {
	
	/** 定年退職コースID */
	private long retirePlanCourseId;
	
	/** 定年退職コースCD */
	private String retirePlanCourseCode;
	
	/** 定年退職コース名 */
	private String retirePlanCourseName;
	
	/** 定年退職コース区分 */
	private int retirePlanCourseClass;
	
	/** 定年年齢 */
	private int retirementAge;
	
	/** 継続区分 */
	private int durationFlg;
	
}
