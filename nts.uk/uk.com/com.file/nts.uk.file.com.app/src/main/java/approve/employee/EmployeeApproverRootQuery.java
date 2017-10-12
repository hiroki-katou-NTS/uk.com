package approve.employee;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class EmployeeApproverRootQuery {
	private GeneralDate baseDate;
	private List<String> lstEmpIds;
	private List<String> lstApps;
	private int rootAtr;

}
