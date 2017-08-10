package nts.uk.ctx.at.shared.dom.relationship.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.relationship.Relationship;

public interface RelationshipRepository {
	/**
	 * get all data
	 * @param companyId
	 * @return
	 */
	List<Relationship> getAll(String companyId);
	/**
	 * author: Hoang Yen
	 * @param relationship
	 */
	void updateRelationship(Relationship relationship);
	/**
	 * author: Hoang Yen
	 * @param relationship
	 */
	void insertRelationship(Relationship relationship);
	/**
	 * author: Hoang Yen
	 * @param companyId
	 * @param relationshipCd
	 */
	void deleteRelationship(String companyId, String relationshipCd);
	/**
	 * 
	 * @param companyId
	 * @param relationshipCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<Relationship> getByCode(String companyId, String relationshipCd);
}
