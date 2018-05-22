package nts.uk.ctx.bs.employee.pub.contact;

import java.util.List;

public interface EmployeeContactPub {
	
	/**
	 * RequestList 378
	 * 社員ID（List）から社員連絡先を取得
	 * @return
	 */
	List<EmployeeContactObject> getList(List<String> employeeIds);
	
	/**
	 * RequestList 380
	 * 社員連絡先を登録する
	 * @param employeeId
	 * @param mailAddress
	 * @param phoneMailAddress
	 * @param cellPhoneNo
	 */
	void register(String employeeId, String mailAddress, String phoneMailAddress, String cellPhoneNo );
	
}
