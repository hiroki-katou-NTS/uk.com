package nts.uk.ctx.hr.develop.ws.test.param;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;

@Getter
@Setter
@NoArgsConstructor
public class ParamCommon {
	
	public String companyId;
	
	public String historyId;
	
	public GeneralDate baseDate;
	
	public GeneralDate startDate;
	
	public GeneralDate endDate;
	
	public Integer retirementAge;
	
	public List<String> departmentId;
	
	public List<String> employmentCode;
	
	public List<String> retiredEmployeeId;
	
	public List<ReferEvaluationItemTestDto> referEvaluationTerm;
	
	public List<RetirePlanParamTestDto> retirePlan;
	
	public Integer reachedAgeTerm;
	
	public RetireDateTermTestDto retireDateTerm;
	
	public List<EmploymentDateDto> closingDate;
	
	public List<EmploymentDateDto> attendanceDate;
	
}
