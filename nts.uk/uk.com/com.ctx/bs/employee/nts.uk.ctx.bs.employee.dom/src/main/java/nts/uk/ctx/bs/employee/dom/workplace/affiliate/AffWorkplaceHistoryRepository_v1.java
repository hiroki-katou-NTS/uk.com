/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface AffiliationWorkplaceHistoryRepository.
 */
public interface AffWorkplaceHistoryRepository_v1 {
	/**
	 * get AffWorkplaceHistory_ver1 by employee id and stand date
	 * @param employeeId
	 * @param generalDate
	 * @return AffWorkplaceHistory_ver1
	 */
	Optional<AffWorkplaceHistory_ver1> getByEmpIdAndStandDate(String employeeId, GeneralDate generalDate);
	
	/**
	 *  get AffWorkplaceHistory_ver1 by history id
	 * @param histId
	 * @return AffWorkplaceHistory_ver1
	 */
	Optional<AffWorkplaceHistory_ver1> getByHistId(String histId);
	
	Optional<AffWorkplaceHistory_ver1> getByEmployeeId(String companyId, String employeeId);
	
	Optional<AffWorkplaceHistory_ver1> getByEmployeeIdDesc(String companyId, String employeeId);
	
	List<AffWorkplaceHistory_ver1> findByEmployees(List<String> employeeIds, GeneralDate date);
	
	Optional<AffWorkplaceHistory_ver1> getByHistIdAndBaseDate(String histId, GeneralDate date);
	/**
	 * ドメインモデル「所属職場」を新規登録する
	 * @param item
	 * @param sid
	 * @param cid
	 */
	void add(String cid, String sid, DateHistoryItem item);
	/**
	 * ドメインモデル「所属職場」を削除する
	 * @param histId
	 */
	void delete(String histId);
	
	/**
	 * ドメインモデル「所属職場」を取得する
	 * @param item
	 */
	void update(DateHistoryItem item);
	
	List<AffWorkplaceHistory_ver1> getWorkplaceHistoryByEmployeeIdAndDate(GeneralDate baseDate, String employeeId);
	
	List<AffWorkplaceHistory_ver1> getWorkplaceHistoryByWkpIdsAndDate(GeneralDate baseDate, List<String> workplaceIds);
	
	List<AffWorkplaceHistory_ver1> getWorkplaceHistoryByEmpIdsAndDate(GeneralDate baseDate, List<String> employeeIds);
	
	List<AffWorkplaceHistory_ver1> getWorkplaceHistoryByWorkplaceIdAndDate(GeneralDate baseDate, String workplaceId);
	
	List<AffWorkplaceHistory_ver1> getWorkplaceHistoryByWkpIdsAndEmpIdsAndDate(GeneralDate baseDate, List<String> employeeIds, List<String> workplaceIds);
	
	List<String> getByWplIdAndPeriod(String workplaceId,GeneralDate startDate, GeneralDate endDate);
	
	List<AffWorkplaceHistory_ver1> getByListSid(List<String> employeeIds);
}
