package approve.employee;

import java.util.List;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Data
public class EmployeeApproverRootQuery {
	private GeneralDate baseDate;
	private List<EmployeeQuery> lstEmpIds;
	private List<String> lstApps;
	private int rootAtr;

}
