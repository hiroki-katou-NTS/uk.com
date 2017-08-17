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
	List<GrantRelationship> getAll(String companyId);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void updateGrantRelationship(GrantRelationship grantRelationship);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void insertGrantRelationship(GrantRelationship grantRelationship);
	/**
	 * author: Hoang Yen
	 * @param grantRelationship
	 */
	void deleteGrantRelationship(GrantRelationship grantRelationship);
	/**
	 * 
	 * @param companyId
	 * @param specialHolidayCd
	 * @param relationshipCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<Relationship> getByCode(String companyId, String specialHolidayCd, String relationshipCd);
}
