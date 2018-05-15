package approve.employee;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class EmployeeApproverRootQuery {
	private GeneralDate baseDate;
	private List<EmployeeQuery> lstEmpIds;
	//list AppType print
	private List<Integer> lstApps;
	//rootAtr = 0: common;
	private int rootAtr;

}
