package nts.uk.ctx.bs.employee.pub.employment;

import java.util.List;

public interface IEmploymentHistoryPub {
	
	/**
	 * RequestList326
	 * @param sID
	 * @return List<社員の雇用履歴>
	 */
	List<EmploymentHisOfEmployee> getEmploymentHisBySid(String sID);
}
