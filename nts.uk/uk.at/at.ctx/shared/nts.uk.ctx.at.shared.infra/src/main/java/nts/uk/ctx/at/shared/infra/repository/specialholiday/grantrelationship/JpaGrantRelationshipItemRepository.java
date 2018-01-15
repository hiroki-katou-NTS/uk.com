package nts.uk.ctx.at.shared.infra.repository.specialholiday.grantrelationship;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.GrantRelationship;
import nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.repository.GrantRelationshipRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantrelationship.KshstGrantRelationshipItem;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantrelationship.KshstGrantRelationshipPK;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaGrantRelationshipItemRepository extends JpaRepository implements GrantRelationshipRepository{
	
	private final String SELECT_NO_WHERE = "SELECT c FROM KshstGrantRelationshipItem c ";
	private final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.kshstGrantRelationshipPK.companyId = :companyId ";
	private final String SELECT_BY_CODE = SELECT_ITEM + "AND c.kshstGrantRelationshipPK.specialHolidayCode = :specialHolidayCode";
	
	/**
	 * change from entity to domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static GrantRelationship toDomain(KshstGrantRelationshipItem entity){
		GrantRelationship domain = GrantRelationship.createFromJavaType(entity.kshstGrantRelationshipPK.companyId, 
																		entity.kshstGrantRelationshipPK.specialHolidayCode,
																		entity.kshstGrantRelationshipPK.relationshipCode,
																		entity.grantRelationshipDay,
																		entity.morningHour);
		return domain;
	}
	
	/**
	 * change from domain to entity
	 * @param domain
	 * @return
	 */
	private static KshstGrantRelationshipItem toEntity(GrantRelationship domain){
		val entity = new KshstGrantRelationshipItem();
		entity.kshstGrantRelationshipPK = new KshstGrantRelationshipPK(domain.getCompanyId(), domain.getSpecialHolidayCode(), domain.getRelationshipCode());
		entity.grantRelationshipDay = domain.getGrantRelationshipDay().v();
		entity.morningHour = domain.getMorningHour() != null ? domain.getMorningHour().v() : null;
		return entity;
	}
	
	/**
	 * get all data
	 * author: Hoang Yen
	 */
	@Override
	public List<GrantRelationship> findAll(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KshstGrantRelationshipItem.class).setParameter("companyId", companyId).getList(c->toDomain(c));
	}
	
	/**
	 * get data by special code
	 * @author yennth
	 */
	@Override
	public List<GrantRelationship> findBySPCode(String companyId, String specialHolidayCode) {
		return this.queryProxy().query(SELECT_BY_CODE, KshstGrantRelationshipItem.class)
								.setParameter("companyId", companyId)
								.setParameter("specialHolidayCode", specialHolidayCode)
								.getList(c -> toDomain(c));
	}
	
	/**
	 * update grant relationship
	 * author: Hoang Yen
	 */
	@Override
	public void update(GrantRelationship grantRelationship) {
		KshstGrantRelationshipPK kshstGrantRelationshipPK = new KshstGrantRelationshipPK(grantRelationship.getCompanyId(), grantRelationship.getSpecialHolidayCode(), grantRelationship.getRelationshipCode());
		KshstGrantRelationshipItem oldEntity = this.queryProxy().find(kshstGrantRelationshipPK, KshstGrantRelationshipItem.class).get();
		oldEntity.grantRelationshipDay = grantRelationship.getGrantRelationshipDay().v();
		oldEntity.morningHour = grantRelationship.getMorningHour() != null ? grantRelationship.getMorningHour().v() : null;;
		this.commandProxy().update(oldEntity);
	}
	
	/**
	 * insert grant relationship
	 * author: Hoang Yen
	 */
	@Override
	public void insert(GrantRelationship grantRelationship) {
		this.commandProxy().insert(toEntity(grantRelationship));
	}
	
	/**
	 * get grant relationship by code
	 * author: Hoang Yen
	 */
	@Override
	public Optional<GrantRelationship> findByCode(String companyId, String specialHolidayCode, String relationshipCode) {
		return this.queryProxy().find(new KshstGrantRelationshipPK(companyId, specialHolidayCode, relationshipCode), KshstGrantRelationshipItem.class).map(c-> toDomain(c));
	}
	
	/**
	 * delete grant relation ship
	 * author: Hoang Yen
	 */
	@Override
	public void delete(String companyId, String specialHolidayCode, String relationshipCode) {
		KshstGrantRelationshipPK kshstGrantRelationshipPK = new KshstGrantRelationshipPK(companyId, specialHolidayCode, relationshipCode);
		this.commandProxy().remove(KshstGrantRelationshipItem.class, kshstGrantRelationshipPK);
	}

	
}
