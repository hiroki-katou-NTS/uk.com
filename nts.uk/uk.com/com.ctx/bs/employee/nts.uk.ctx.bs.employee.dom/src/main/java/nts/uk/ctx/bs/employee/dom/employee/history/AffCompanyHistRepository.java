package nts.uk.ctx.bs.employee.dom.employee.history;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AffCompanyHistRepository {
	/** add new affiliation history */
	void add(AffCompanyHist domain);

	/** update one affiliation history */
	void update(AffCompanyHist domain);

	/** remove all affiliation history of one person */
	void remove(AffCompanyHist domain);

	/** remove one affiliation by personId & employeeId */
	void remove(String pId, String employeeId, String hisId);

	/** remove affiliation history of one employee */
	void remove(String pId, String employeeId);

	/** remove all affiliation history of one person */
	void remove(String pId);

	AffCompanyHist getAffCompanyHistoryOfPerson(String personId);

	AffCompanyHist getAffCompanyHistoryOfEmployee(String employeeId);
	
	AffCompanyHist getAffCompanyHistoryOfEmployeeDesc(String cid, String employeeId);
	
	/**
	 * đối ứng cho thuật toán [次回特休情報を取得する]
	 * @param cid
	 * @param sids
	 * @return
	 */
	Map<String, AffCompanyHist> getAffCompanyHistoryOfEmployee(String cid, List<String> sids);
	
	List<AffCompanyHist> getAffCompanyHistoryOfEmployees(List<String> employeeIds);
	
	/**
	 * return AffCompanyHistByEmployee
	 * @param employeeIds
	 * @return
	 */
	List<AffCompanyHistByEmployee> getAffEmployeeHistory(List<String> employeeIds);
	
	List<AffCompanyHistByEmployee> getAffEmployeeHistory(List<String> employeeIds , DatePeriod datePeriod);

	AffCompanyHist getAffCompanyHistoryOfEmployeeAndBaseDate(String employeeId, GeneralDate baseDate);

	List<AffCompanyHist> getAffCompanyHistoryOfEmployeeListAndBaseDate(List<String> employeeIds, GeneralDate baseDate);
	
	AffCompanyHist getAffCompanyHistoryOfHistInfo(String histId);

	/** Hop.NT */

	/** Add new affiliation history */
	void add(String sid, String pId, AffCompanyHistItem item);

	/** Update one affiliation history */
	void update(AffCompanyHistItem item);

	/** End */
	
	List<AffCompanyHist> getAffComHisEmpByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod);
	
	List<String> getLstSidByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod);
	
	/**
	 * @author lanlt
	 * @param employeeIds
	 * @param baseDate
	 * @return
	 */
	List<AffCompanyHist> getAffComHistOfEmployeeListAndBaseDateV2(List<String> employeeIds, GeneralDate baseDate);
	/**
	 * @author lanlt
	 * add new affiliation histories  
	 * @param domains
	 */
	void addAll(List<AffCompanyHistCustom> domains);
	/**
	 * @author lanlt
	 * update all AffCompanyHistItems
	 * @param items
	 */
	void updateAll(List<AffCompanyHistItem> items);

	// get data cps013
	List<AffCompanyHist> getAffComHistOfEmployeeListAndNoBaseDateV3(List<String> sids);
}
