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
	List<Relationship> findAll(String companyId);
	/**
	 * author: Hoang Yen
	 * @param relationship
	 */
	void update(Relationship relationship);
	/**
	 * author: Hoang Yen
	 * @param relationship
	 */
	void insert(Relationship relationship);
	/**
	 * author: Hoang Yen
	 * @param companyId
	 * @param relationshipCd
	 */
	void delete(String companyId, String relationshipCd);
	/**
	 * 
	 * @param companyId
	 * @param relationshipCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<Relationship> findByCode(String companyId, String relationshipCd);
	
	/**
	 * get all data with setting
	 * @param companyId
	 * @param sHENo 
	 * @return
	 */
	
	List<String> findSettingWithCds(String companyId, int sHENo, List<String> relpCds);
}
