package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

//定年退職予定者
@Getter
public class RetirementPlannedPersonDto {

	private String personalId;
	
	private String employeeId;
	
	private String employeeCode;
	
	private String businessName;
	
	private String businessNameKana;
	
	private GeneralDate birthday;
	
	private GeneralDate dateJoinComp;
	
	private String departmentId;
	
	private String departmentCode;
	
	private String departmentName;
	
	private String positionId;
	
	private String positionCode;
	
	private String positionName;
	
	private String employmentCode;
	
	private String employmentName;
	
	private int age;
	
	private GeneralDate retirementDate;
	
	private GeneralDate releaseDate;
	
	private Optional<String> hrEvaluation1;
	
	private Optional<String> hrEvaluation2;
	
	private Optional<String> hrEvaluation3;
	
	private Optional<String> healthAssessment1;
	
	private Optional<String> healthAssessment2;
	
	private Optional<String> healthAssessment3;
	
	private Optional<String> stressAssessment1;
	
	private Optional<String> stressAssessment2;
	
	private Optional<String> stressAssessment3;

	public RetirementPlannedPersonDto(String personalId, String employeeId, String employeeCode, String businessName,
			String businessNameKana, GeneralDate birthday, GeneralDate dateJoinComp, String departmentId, String departmentCode,
			String departmentName, String positionId, String positionCode, String positionName, String employmentCode,
			String employmentName, Integer age, GeneralDate retirementDate, GeneralDate releaseDate,
			Optional<ComprehensiveEvaluationDto> hrEvaluationList,
			Optional<ComprehensiveEvaluationDto> healthStatusList,
			Optional<ComprehensiveEvaluationDto> stressStatusList) {
		super();
		this.personalId = personalId;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.businessName = businessName;
		this.businessNameKana = businessNameKana;
		this.birthday = birthday;
		this.dateJoinComp = dateJoinComp;
		this.departmentId = departmentId;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.positionId = positionId;
		this.positionCode = positionCode;
		this.positionName = positionName;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
		this.age = age;
		this.retirementDate = retirementDate;
		this.releaseDate = releaseDate;
		this.hrEvaluation1 = hrEvaluationList.isPresent()?Optional.of(hrEvaluationList.get().getOverallResult1()):Optional.empty();
		this.hrEvaluation2 = hrEvaluationList.isPresent()?Optional.of(hrEvaluationList.get().getOverallResult2()):Optional.empty();
		this.hrEvaluation3 = hrEvaluationList.isPresent()?Optional.of(hrEvaluationList.get().getOverallResult3()):Optional.empty();
		this.healthAssessment1 = healthStatusList.isPresent()?Optional.of(healthStatusList.get().getOverallResult1()):Optional.empty();
		this.healthAssessment2 = healthStatusList.isPresent()?Optional.of(healthStatusList.get().getOverallResult2()):Optional.empty();
		this.healthAssessment3 = healthStatusList.isPresent()?Optional.of(healthStatusList.get().getOverallResult3()):Optional.empty();
		this.stressAssessment1 = stressStatusList.isPresent()?Optional.of(stressStatusList.get().getOverallResult1()):Optional.empty();
		this.stressAssessment2 = stressStatusList.isPresent()?Optional.of(stressStatusList.get().getOverallResult2()):Optional.empty();
		this.stressAssessment3 = stressStatusList.isPresent()?Optional.of(stressStatusList.get().getOverallResult3()):Optional.empty();
	}
	
	
}
