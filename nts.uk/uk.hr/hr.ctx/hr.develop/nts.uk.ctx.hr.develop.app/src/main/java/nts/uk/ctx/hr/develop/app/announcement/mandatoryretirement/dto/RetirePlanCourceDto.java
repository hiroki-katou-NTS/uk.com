package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

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

	public RetirePlanCourceDto(long retirePlanCourseId, String retirePlanCourseCode, String retirePlanCourseName,
			int retirePlanCourseClass, int retirementAge, int durationFlg) {
		super();
		this.retirePlanCourseId = retirePlanCourseId;
		this.retirePlanCourseCode = retirePlanCourseCode;
		this.retirePlanCourseName = retirePlanCourseName;
		this.retirePlanCourseClass = retirePlanCourseClass;
		this.retirementAge = retirementAge;
		this.durationFlg = durationFlg;
	}
	
}
