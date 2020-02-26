package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.DepartmentInforImport;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.RequestList643Import;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.ResultRequest600Import;

public interface IDepartmentInforImport {

	/**call RQ 562 */
	List<DepartmentInforImport> getAllActiveDepartment(String companyId, GeneralDate baseDate);
	
	/**call RQ 643 */
	List<RequestList643Import> getAffDeptHistByEmpIdAndBaseDate(List<String> sids, GeneralDate baseDate);
	
	/**call RQ 574 */
	List<String> getDepartmentIdAndChildren(String companyId, GeneralDate baseDate, String departmentId);
	
	/**call RQ 644 */
	List<RequestList643Import> getAffDepartmentHistoryItems(List<String> departmentIDs, GeneralDate baseDate);
	
	/**call RQ 600 */
	List<ResultRequest600Import> getEmpInfoLstBySids(List<String> sids, DatePeriod period, boolean isDelete, boolean isGetAffCompany);
	
	/**call RQ 614 for JMM018Z*/
	List<String> searchEmployeeByKey(String companyId, String key);
}
