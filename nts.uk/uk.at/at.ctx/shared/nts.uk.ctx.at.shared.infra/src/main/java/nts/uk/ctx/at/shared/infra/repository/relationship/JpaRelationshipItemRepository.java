package nts.uk.ctx.at.shared.infra.repository.relationship;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.infra.entity.relationship.KshstRelationshipItem;
import nts.uk.ctx.at.shared.infra.entity.relationship.KshstRelationshipPK;

@Stateless
public class JpaRelationshipItemRepository extends JpaRepository implements RelationshipRepository{
	
	private final String SELECT_NO_WHERE = "SELECT c FROM KshstRelationshipItem c ";
	private final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.kshstRelationshipPK.companyId = :companyId";
	/**
	 * change from entity to domain
	 * @param entity
	 * @return
	 * 
	 * author: Hoang Yen
	 */
	private static Relationship toDomain(KshstRelationshipItem entity){
		Relationship domain = Relationship.createFromJavaType(entity.kshstRelationshipPK.companyId,
													entity.kshstRelationshipPK.relationshipCd, 
													entity.relationshipName);
		return domain;
	}
	
	private static KshstRelationshipItem toEntity(Relationship domain){
		val entity = new KshstRelationshipItem();
		entity.kshstRelationshipPK = new KshstRelationshipPK(domain.getCompanyId(), domain.getRelationshipCd().v());
		entity.relationshipName = domain.getRelationshipName().v();
		return entity;
	}
	
	/**
	 * get all data
	 * 
	 * author: Hoang Yen
	 */
	@Override
	public List<Relationship> getAll(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KshstRelationshipItem.class).setParameter("companyId", companyId).getList(c -> toDomain(c));
	}
	/**
	 * update relationship
	 * author: Hoang Yen
	 */
	@Override
	public void updateRelationship(Relationship relationship) {
		KshstRelationshipItem entity = toEntity(relationship);
		KshstRelationshipItem oldEntity = this.queryProxy().find(entity.kshstRelationshipPK, KshstRelationshipItem.class).get();
		oldEntity.setRelationshipName(entity.relationshipName);
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert relationship
	 * author: Hoang Yen
	 */
	@Override
	public void insertRelationship(Relationship relationship) {
		this.commandProxy().insert(toEntity(relationship));		
	}
	/**
	 * delete relation ship
	 * author: Hoang Yen
	 */
	@Override
	public void deleteRelationship(String companyId, String relationshipCd) {
		KshstRelationshipPK kshstRelationshipPK = new KshstRelationshipPK(companyId, relationshipCd);
		this.commandProxy().remove(KshstRelationshipItem.class, kshstRelationshipPK);
	}
	/**
	 * get relationship by code
	 * author: Hoang Yen
	 */
	@Override
	public Optional<Relationship> getByCode(String companyId, String relationshipCd) {
		return this.queryProxy().find(new KshstRelationshipPK(companyId, relationshipCd), KshstRelationshipItem.class).map(c -> toDomain(c));
	}
	
}
