/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffiliationWorkplaceHistoryRepository.
 */
public interface AffWorkplaceHistoryRepository {
	
	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param workplaces the workplaces
	 * @return the list
	 */
	public List<AffWorkplaceHistory> searchWorkplaceHistory(GeneralDate baseDate, List<String> workplaces);
	
	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param workplaces the workplaces
	 * @return the list
	 */
	public List<AffWorkplaceHistory> searchWorkplaceHistory(List<String> employeeIds, 
			GeneralDate baseDate, List<String> workplaces);
	
	/**
	 * Search workplace history by employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<AffWorkplaceHistory> searchWorkplaceHistoryByEmployee(String employeeId, 
			GeneralDate baseDate);
	
	
	/**
	 * Search workplace of company id.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<AffWorkplaceHistory> searchWorkplaceOfCompanyId(List<String> employeeIds,
			GeneralDate baseDate);
	
	/**
	 * Gets the by workplace ID.
	 *
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the by workplace ID
	 */
	public List<AffWorkplaceHistory> getByWorkplaceID(String workplaceId, GeneralDate baseDate);
	
	/**
	 * Gets the by workplace I ds.
	 *
	 * @param workplaceIds the workplace ids
	 * @param baseDate the base date
	 * @return the by workplace I ds
	 */
	public List<AffWorkplaceHistory> getByWorkplaceIDs(List<String> workplaceIds, GeneralDate baseDate);
	
	/**
	 * ドメインモデル「所属職場」を新規登録する
	 * @param domain
	 */
	void addAffWorkplaceHistory(AffWorkplaceHistory domain);
	/**
	 * ドメインモデル「所属職場」を削除する
	 * @param domain
	 */
	void deleteAffWorkplaceHistory(AffWorkplaceHistory domain);
	
	/**
	 * ドメインモデル「所属職場」を取得する
	 * @param domain
	 */
	void updateAffWorkplaceHistory(AffWorkplaceHistory domain);
}
