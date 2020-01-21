package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RetirePlanCourceParamDto {

	private String companyId;
	
	private long retirePlanCourseId;
	
	private String retirePlanCourseCode;
	
	private String retirePlanCourseName;
	
	private Integer retirePlanCourseClass;
	
	private Integer retirementAge;
	
	private Integer durationFlg;

}
