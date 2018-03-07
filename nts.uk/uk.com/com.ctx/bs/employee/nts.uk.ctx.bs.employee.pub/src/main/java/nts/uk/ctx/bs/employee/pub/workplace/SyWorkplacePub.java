/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.workplace;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface WorkplacePub.
 */
public interface SyWorkplacePub {

	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList #30
	Optional<SWkpHistExport> findBySid(String employeeId, GeneralDate baseDate);

	/**
	 * Find wpk ids by wkp code.
	 *
	 * @param companyId the company id
	 * @param wpkCode the wpk code
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #41
	List<String> findWpkIdsByWkpCode(String companyId, String wpkCode, GeneralDate baseDate);

	/**
	 * Find wpk ids by sid.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param date the date
	 * @return the list
	 */
	// RequestList #65
	List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate date);

	/**
	 * Find by wkp id.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #66
	Optional<WkpCdNameExport> findByWkpId(String workplaceId, GeneralDate baseDate);
	
	/**
	 * Find parent wpk ids by wkp id.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param date the date
	 * @return the list
	 */
	// RequestList #83
	List<String> findParentWpkIdsByWkpId(String companyId, String workplaceId, GeneralDate date);

	/**
	 * Gets the workplace id.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the workplace id
	 */
	String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate);
		
	/**
	 * Find list workplace id by base date.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #157
	List<String> findListWorkplaceIdByBaseDate(GeneralDate baseDate);
	
	/**
	 * Find list workplace id by cid and wkp id and base date.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the list
	 */
	// RequestList #154
	List<String> findListWorkplaceIdByCidAndWkpIdAndBaseDate(String companyId, String workplaceId, GeneralDate baseDate);
	
	/**
	 * 
	 * @param workplaceId
	 * @param period
	 * @return List EmployeeId
	 */
	// RequestList #120
	List<String> findListSIdByCidAndWkpIdAndPeriod(String workplaceId, GeneralDate startDate,GeneralDate endDate);
	
	/**
	 * RequestList No 189
	 * @param sid
	 * @param baseDate
	 * @return
	 */
	List<WorkPlaceHistExport> GetWplByListSidAndPeriod(List<String> sid, DatePeriod datePeriod);
	
	/**
	 * Find by wkp ids at time.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @param wkpIds the wkp ids
	 * @return the list
	 */
	// RequestList #312
	List<WkpConfigAtTimeExport> findByWkpIdsAtTime(String companyId, GeneralDate baseDate, List<String> wkpIds);
}
