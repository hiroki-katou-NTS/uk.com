package nts.uk.ctx.at.shared.dom.grantrelationship.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;

public interface GrantRelationshipRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	List<GrantRelationship> findAll(String companyId);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void update(GrantRelationship grantRelationship);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void insert(GrantRelationship grantRelationship);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void delete(GrantRelationship grantRelationship);
	/**
	 * 
	 * @param companyId
	 * @param specialHolidayCd
	 * @param relationshipCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<Relationship> findByCode(String companyId, String specialHolidayCode, String relationshipCode);
}
