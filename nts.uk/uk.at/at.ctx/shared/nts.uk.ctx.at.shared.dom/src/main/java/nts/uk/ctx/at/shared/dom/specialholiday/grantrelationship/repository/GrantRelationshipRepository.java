package nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.GrantRelationship;
/**
 * 
 * @author yennth
 *
 */
public interface GrantRelationshipRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	List<GrantRelationship> findAll(String companyId);
	
	/**
	 * get data by special code
	 * @param companyId
	 * @param specialHolidayCode
	 * @return
	 */
	List<GrantRelationship> findBySPCode(String companyId, String specialHolidayCode);
	
	/**
	 * update item 
	 * @param grantRelationship
	 */
	void update(GrantRelationship grantRelationship);
	
	/**
	 * insert a item
	 * @param grantRelationship
	 */
	void insert(GrantRelationship grantRelationship);
	
	/**
	 * delete a item
	 * @param grantRelationship
	 */
	void delete(String companyId, String specialHolidayCode, String relationshipCode);
	
	/**
	 * find a item by code
	 * @param companyId
	 * @param specialHolidayCd
	 * @param relationshipCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<GrantRelationship> findByCode(String companyId, String specialHolidayCode, String relationshipCode);
}
