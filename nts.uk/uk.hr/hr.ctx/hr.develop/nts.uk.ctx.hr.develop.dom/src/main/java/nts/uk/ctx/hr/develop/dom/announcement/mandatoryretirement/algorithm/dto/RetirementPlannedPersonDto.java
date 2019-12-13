package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//定年退職予定者
@AllArgsConstructor
@Getter
public class RetirementPlannedPersonDto {

	private String personalId;
	
	private String employeeId;
	
	private String employeeCode;
	
	private String businessName;
	
	private String businessNameKana;
	
	private String birthday;
	
	private String hireDate;
	
	private String departmentId;
	
	private String departmentCode;
	
	private String departmentName;
	
	private String jobId;
	
	private String positionCode;
	
	private String jobTitle;
	
	private String employmentCode;
	
	private String employmentName;
	
	private String age;
	
	private String retirementDate;
	
	private String releaseDate;
	
	private String personnelEvaluation1;
	
	private String personnelEvaluation2;
	
	private String personnelEvaluation3;
	
	private String healthAssessment1;
	
	private String healthAssessment2;
	
	private String healthAssessment3;
	
	private String stressAssessment1;
	
	private String stressAssessment2;
	
	private String stressAssessment3;
}
