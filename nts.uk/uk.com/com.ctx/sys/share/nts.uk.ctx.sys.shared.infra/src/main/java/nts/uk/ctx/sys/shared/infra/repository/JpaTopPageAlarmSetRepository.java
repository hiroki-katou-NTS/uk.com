package nts.uk.ctx.sys.shared.infra.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSet;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSetRepository;
import nts.uk.ctx.sys.shared.infra.entity.KrcstToppageAlarmSet;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaTopPageAlarmSetRepository extends JpaRepository implements TopPageAlarmSetRepository {
	private static final String SELECT_NO_WHERE = "SELECT c FROM KrcstToppageAlarmSet c ";
	private static final String SELECT_BY_COM = SELECT_NO_WHERE + "WHERE c.companyId = :companyId ";
	private static final String SELECT_BY_ALARMCATEGORY = SELECT_BY_COM + "AND c.alarmCategory = :alarmCategory ";

	/**
	 * convert from top page alarm set entity to domain
	 * 
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private TopPageAlarmSet toDomain(KrcstToppageAlarmSet entity) {
		return TopPageAlarmSet.createFromJavaType(entity.companyId, entity.alarmCategory, entity.useAtr);
	}

	private KrcstToppageAlarmSet toEntity(TopPageAlarmSet domain) {
		val entity = new KrcstToppageAlarmSet();
		entity.companyId = domain.getCompanyId();
		entity.alarmCategory = domain.getAlarmCategory().value;
		entity.useAtr = domain.getUseAtr().value;
		return entity;
	}

	/**
	 * find all item
	 * 
	 * @author yennth
	 */
	@Override
	public List<TopPageAlarmSet> getAll(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM, KrcstToppageAlarmSet.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	/**
	 * update a top page alarm set
	 * 
	 * @author yennth
	 */
	@Override
	public void update(TopPageAlarmSet topPageAlarmSet) {
		this.commandProxy().update(toEntity(topPageAlarmSet));
	}
	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSetRepository#delete(java.lang.String)
	 * delete TopPageAlarmSet by companyID
	 */
	@Override
	public void delete(String companyID) {
		List<TopPageAlarmSet> listTopPageAlarmSet = getAll(companyID);
		for (TopPageAlarmSet topPageAlarmSet : listTopPageAlarmSet) {
			this.commandProxy().remove(topPageAlarmSet);
		}

	}

	@Override
	public void create(TopPageAlarmSet topPageAlarmSet) {
		this.commandProxy().insert(topPageAlarmSet);
	}
	
	// get toppage alarm set by alarmCategory
	@Override
	public Optional<TopPageAlarmSet> getByAlarmCategory(String companyId, int alarmCategory) {
		KrcstToppageAlarmSet entity = this.queryProxy().query(SELECT_BY_ALARMCATEGORY, KrcstToppageAlarmSet.class)
								.setParameter("alarmCategory", alarmCategory)
								.setParameter("companyId", companyId)
								.getSingleOrNull();
		if(entity == null){
			return Optional.empty();
		}else{
			return Optional.of(this.toDomain(entity));
		}
	}

}
