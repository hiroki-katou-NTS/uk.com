/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.executionlog;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.AffWorkplaceHistoryItem;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.WorkplaceDto;

/**
 * The Interface ScWorkplaceAdapter.
 */
public interface ScWorkplaceAdapter {
    
    /**
     * Find wpk id list.
     *
     * @param companyId the company id
     * @param wpkCode the wpk code
     * @param baseDate the base date
     * @return the list
     */
    List<String> findWpkIdList(String companyId, String wpkCode, Date baseDate);
    
    
    /**
     * Find work place by id.
     *
     * @param employeeId the employee id
     * @param baseDate the base date
     * @return the optional
     */
    public Optional<WorkplaceDto> findWorkplaceById(String employeeId, GeneralDate baseDate);
    
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
	 * [No.571]職場の上位職場を基準職場を含めて取得する
	 *
	 * @param companyId
	 * @param baseDate
	 * @param workplaceId
	 * @return
	 */
	List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId);
	
	/**
	 * [No.650]社員が所属している職場を取得する
	 * 社員と基準日から所属職場履歴項目を取得する
	 * @param employeeID
	 * @param date
	 * @return
	 */
	 AffWorkplaceHistoryItem getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);

}
