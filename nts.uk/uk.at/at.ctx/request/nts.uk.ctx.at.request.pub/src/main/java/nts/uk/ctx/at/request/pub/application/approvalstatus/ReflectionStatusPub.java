package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
public interface ReflectionStatusPub {

//	public List<ApplicationNewExport> getByListRefStatus(String employeeID ,GeneralDate startDate, GeneralDate endDate , List<Integer> listReflecInfor);
	/**
	 * [No.690]社員（List）,期間に一致する申請を取得する
	 * @param employeeIDS
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, List<ApplicationDateExport>> getAppByEmployeeDate(List<String> employeeIDS, 
																		GeneralDate startDate, GeneralDate endDate);
}
