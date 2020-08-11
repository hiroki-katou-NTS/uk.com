/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface WorkingConditionRepository.
 */
public interface WorkingConditionRepository {

	/**
	 * Gets the by sid.
	 *
	 * @param sId the s id
	 * @return the by sid
	 */
	Optional<WorkingCondition> getBySid(String sId);

	/**
	 * Gets the by sid.
	 *
	 * @param companyId the company id
	 * @param sId the s id
	 * @return the by sid
	 */
	Optional<WorkingCondition> getBySid(String companyId, String sId);

	/**
	 * Gets the by history id.
	 *
	 * @param historyId the history id
	 * @return the by history id
	 */
	Optional<WorkingCondition> getByHistoryId(String historyId);

	/**
	 * Gets the by sid and standard date.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the by sid and standard date
	 */
	Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate);
	
	/**
	 * Gets the by sid and standard date.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the by sid and standard date
	 */
	Optional<WorkingCondition> getBySidAndStandardDate(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * Gets the by sids.
	 *
	 * @param sId the s id
	 * @return the by sids
	 */
	List<WorkingCondition> getBySidsAndDatePeriod(List<String> sIds, DatePeriod datePeriod);
	
	List<WorkingCondition> getBySidsAndDatePeriodNew(List<String> sIds, DatePeriod datePeriod);
	
	List<WorkingCondition> getBySids(List<String> employeeIds,GeneralDate baseDate);
	/**
	 * Save.
	 *
	 * @param workingCondition the working condition
	 */
	void save(WorkingCondition workingCondition);
	
	/**
	 * Save.
	 *
	 * @param workingCondition the working condition
	 */
	void saveAll(List<WorkingCondition> workingConditions);

	/**
	 * Delete.
	 *
	 * @param employeeId the employee id
	 */
	void delete(String employeeId);
	/**
	 * fixed r
	 * get WorkingCondtion by sids, cid, baseDate
	 * @param employeeIds
	 * @param baseDate
	 * @param cid
	 * @return
	 */
	List<WorkingCondition> getBySidsAndCid(List<String> employeeIds,GeneralDate baseDate, String cid);

	/**
	 * @author lanlt
	 * Get all the by sids and cid.
	 * @param cid the company id
	 * @return the by sid
	 */
	List<WorkingCondition> getBySidsAndCid(String companyId, List<String> sIds);
	/**
	 * [0] insert ( 労働条件, 労働条件項目 )			
	 * @param workingCondition
	 * @param workingConditionItem
	 */
	void insert (WorkingCondition workingCondition , WorkingConditionItem workingConditionItem);
	
	/**
	 * [1] update ( 労働条件, 労働条件項目 )
	 * @param workingCondition
	 * @param workingConditionItem
	 */
	void update (WorkingCondition workingCondition , WorkingConditionItem workingConditionItem);
	/**
	 * [2-1] delete ( 会社ID, 社員ID, 履歴ID )		
	 * @param companyID
	 * @param empID
	 * @param hisID
	 */
	void delete (String companyID ,String empID,String histID);
	/**
	 * [2-2] delete ( 会社ID, 社員ID )	
	 * @param companyID
	 * @param empID
	 */
	void delete (String companyID ,String empID);
	/**
	 * [3-1] 社員IDを指定して履歴を取得する ( 会社ID, 社員ID )
	 * @param companyID
	 * @param empID
	 * @return
	 */
	Optional<WorkingCondition> getWorkingCondition(String companyID , String empID);
	/**
	 * [3-2] *社員IDを指定して履歴を取得する ( 会社ID, List<社員ID> )	
	 * @param histID
	 * @return
	 */
	List<WorkingCondition> getWorkingConditionByListEmpID(String companyID,List<String> lstEmpID);
	/**
	 * [4-1] 履歴IDを指定して履歴項目を取得する ( 履歴ID )
	 * @param histID
	 * @return
	 */
	Optional<WorkingConditionItem> getWorkingConditionItem(String histID);
	/**
	 * [4-2] *履歴IDを指定して履歴項目を取得する ( List<履歴ID> )		
	 * @param listHistID
	 * @return
	 */
	List<WorkingConditionItem> getWorkingConditionItemByListHistID(List<String> listHistID);
	/**
	 * [5] 年月日時点の履歴項目を取得する 1
	 * @param companyID
	 * @param ymd
	 * @return
	 */
	List<WorkingConditionItem> getWorkingConditionItemByCompanyIDAndDate(String companyID , GeneralDate ymd);
	/**
	 * [6-1] 社員を指定して年月日時点の履歴項目を取得する ( 会社ID, 年月日, 社員ID ) 1
	 * @param companyID
	 * @param ymd
	 * @param empID
	 * @return
	 */
	Optional<WorkingConditionItem> getWorkingConditionItemByEmpIDAndDate(String companyID , GeneralDate ymd,String empID);
	/**
	 *[6-2] *社員を指定して年月日時点の履歴項目を取得する ( 会社ID, 年月日, List<社員ID> )		
	 * @param companyID
	 * @param ymd
	 * @param empID
	 * @return
	 */
	List<WorkingConditionItem> getWorkingConditionItemByLstEmpIDAndDate (String companyID , GeneralDate ymd,List<String> empID);
	/**
	 * [7] 期間付き履歴項目を取得する	
	 * @param companyID
	 * @param lstEmpID
	 * @param datePeriod
	 * @return
	 */
	List<WorkingConditionItemWithPeriod> getWorkingConditionItemWithPeriod(String companyID , List<String> lstEmpID ,DatePeriod datePeriod );
}
