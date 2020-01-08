package nts.uk.ctx.hr.develop.app.jmm018retire.dto;

import lombok.Data;

@Data
public class PlanCourseTermDto {
	
	/** 申請開始年齢  */
	private Integer applicationEnableStartAge;
	
	/** 申請終了年齢 */
	private Integer applicationEnableEndAge;
	
	/** 申請終了月 */
	private int endMonth;
	
	/** 申請終了日 */
	private int endDate;

	public PlanCourseTermDto(Integer applicationEnableStartAge, Integer applicationEnableEndAge, int endMonth,
			int endDate) {
		super();
		this.applicationEnableStartAge = applicationEnableStartAge;
		this.applicationEnableEndAge = applicationEnableEndAge;
		this.endMonth = endMonth;
		this.endDate = endDate;
	}
	
}
