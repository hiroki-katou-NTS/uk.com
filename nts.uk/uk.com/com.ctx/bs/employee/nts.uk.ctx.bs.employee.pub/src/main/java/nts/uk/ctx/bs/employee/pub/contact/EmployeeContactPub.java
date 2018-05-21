package nts.uk.ctx.bs.employee.pub.contact;

import java.util.List;

public interface EmployeeContactPub {
	
	/**
	 * RequestList 378
	 * 社員ID（List）から社員連絡先を取得
	 * @return
	 */
	List<EmployeeContactObject> getList(List<String> employeeIds);

}
