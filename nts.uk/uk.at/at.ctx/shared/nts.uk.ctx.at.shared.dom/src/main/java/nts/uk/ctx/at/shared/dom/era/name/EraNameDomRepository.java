package nts.uk.ctx.at.shared.dom.era.name;

import java.util.List;

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
