package nts.uk.ctx.hr.develop.ws.test.param;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

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
	
}
