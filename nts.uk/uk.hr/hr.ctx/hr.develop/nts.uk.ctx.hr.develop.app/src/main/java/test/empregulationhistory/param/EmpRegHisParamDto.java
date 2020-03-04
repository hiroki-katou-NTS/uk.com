package test.empregulationhistory.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpRegHisParamDto {

	public String cId;
	
	public String historyId;
	
	public GeneralDate startDate;
}
