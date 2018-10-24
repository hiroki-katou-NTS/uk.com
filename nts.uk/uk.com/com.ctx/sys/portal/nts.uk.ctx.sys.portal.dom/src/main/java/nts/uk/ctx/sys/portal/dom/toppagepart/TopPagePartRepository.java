package nts.uk.ctx.sys.portal.dom.toppagepart;

import java.util.List;
import java.util.Optional;

/**
 * @author LamDT
 */
public interface TopPagePartRepository {
	
	/**
	 * @param cID
	 * @param topPagePartID
	 * @return
	 */
	Optional<TopPagePart> findByKey(String cID, String topPagePartID);
	/**
	 * Find all TopPagePart
	 *
	 * @param companyID
	 * @return List TopPagePart
	 */
	List<TopPagePart> findAll(String companyID);

	/**
	 * Find TopPagePart by Type
	 *
	 * @param type
	 * @return List TopPagePart
	 */
	List<TopPagePart> findByType(String companyID, int type);
	
	/**
	 * Find all TopPagePart by TopPagePartTypes
	 *
	 * @param companyID
	 * @param topPagePartTypes List TopPagePartType you need to find in
	 * @return List TopPagePart
	 */
	List<TopPagePart> findByTypes(String companyID, List<Integer> topPagePartTypes);
	
	/**
	 * Find all TopPagePart by TopPagePartType and IDs
	 *
	 * @param companyID
	 * @param topPagePartTypes List TopPagePartType you need to find in
	 * @param topPagePartIDs List active TopPagePartID you need to find in
	 * @return List TopPagePart
	 */
	List<TopPagePart> findByTypesAndIDs(String companyID, List<Integer> topPagePartTypes, List<String> topPagePartIDs);
	
	/**
	 * find top page part data by code and type
	 * @param companyId
	 * @param code
	 * @param type
	 * @return
	 */
	Optional<TopPagePart> findByCodeAndType(String companyId, String code, int type);
	
	/**
	 * Remove a TopPagePart
	 *
	 * @param companyID
	 * @param topPagePartID
	 */
	void remove(String companyID, String topPagePartID);

	/**
	 * Add a TopPagePart
	 *
	 * @param topPagePart
	 */
	void add(TopPagePart topPagePart);

	/**
	 * update a TopPagePart
	 *
	 * @param topPagePart
	 */
	void update(TopPagePart topPagePart);

}
