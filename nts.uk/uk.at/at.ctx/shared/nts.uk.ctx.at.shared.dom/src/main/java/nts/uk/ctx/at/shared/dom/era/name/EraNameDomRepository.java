package nts.uk.ctx.at.shared.dom.era.name;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EraNameDomRepository.
 */
public interface EraNameDomRepository {

	/**
	 * Gets the all era name.
	 *
	 * @return the all era name
	 */
	List<EraNameDom> getAllEraName();
	
	/**
	 * Gets the era name.
	 *
	 * @param eraNameId the era name id
	 * @return the era name
	 */
	EraNameDom getEraNameById(String eraNameId);
	
	/**
	 * Gets the era name by start date.
	 *
	 * @param strDate the str date
	 * @return the era name by start date
	 */
	EraNameDom getEraNameByStartDate(GeneralDate strDate);
	
	/**
	 * Gets the era name by end date.
	 *
	 * @param endDate the end date
	 * @return the era name by end date
	 */
	EraNameDom getEraNameByEndDate(GeneralDate endDate);
	
	/**
	 * Delete era name.
	 *
	 * @param eraNameId the era name id
	 */
	void deleteEraName(String eraNameId);
	
	/**
	 * Update era name.
	 *
	 * @param domain the domain
	 */
	void updateEraName(EraNameDom domain);
	
	/**
	 * Adds the new era name.
	 *
	 * @param domain the domain
	 */
	void addNewEraName(EraNameDom domain);
}
