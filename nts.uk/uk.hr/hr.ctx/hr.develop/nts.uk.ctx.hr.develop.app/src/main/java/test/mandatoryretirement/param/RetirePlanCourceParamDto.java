package test.mandatoryretirement.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import test.mandatoryretirement.dto.RetirePlanCourceDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RetirePlanCourceParamDto {

	public String cId;
	
	public GeneralDate baseDate;
	
	public List<RetirePlanCourceDto> retirePlanCourseList;
	
	public List<String> retirePlanCourseId;
}
