/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.wkpmanager;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmpInfoImport;
import nts.uk.ctx.sys.auth.dom.wkpmanager.dom.EmpBasicInfoImport;
import nts.uk.ctx.sys.auth.dom.wkpmanager.dom.PersonInfoImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface PersonInfoAdapter.
 */
public interface EmpInfoAdapter {
	
	/**
	 * get list person by list person ID
	 * @param personId
	 * @return
	 */
	List<PersonInfoImport> getByListId(List<String> personId);
	
	/**
	 * Gets the list person info.
	 *
	 * @param listSid the list sid
	 * @return the list person info
	 */
	List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid);
	
	// RequestList61
	List<EmpInfoImport> getEmpInfo(List<String> lstSid);

	//RequestList466
	//期間内に特定の職場（List）に所属している社員一覧を取得
	List<String> getListEmployeeId(List<String> wkpIds, DatePeriod dateperiod);
	//RequestList515
	//職位ID（List）と基準日から該当する社員一覧を取得する
	List<String> getListEmployee(List<String> jobTitleIds, GeneralDate baseDate);
}