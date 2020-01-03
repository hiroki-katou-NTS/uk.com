package nts.uk.ctx.hr.develop.ws.test.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirePlanParam;

@NoArgsConstructor
@Getter
public class RetirePlanParamTestDto {
	
	public String employeeId;
	
	public String employmentCode;
	
	public GeneralDate birthday;
	
	public Integer retirementAge;
	
	public RetirePlanParam toDomain() {
		return new RetirePlanParam(this.employeeId, this.employmentCode, this.birthday, this.retirementAge);
	}
}
