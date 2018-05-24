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
public class JpaTopPageAlarmSetRepository extends JpaRepository implements TopPageAlarmSetRepository{
	private final String SELECT_NO_WHERE = "SELECT c FROM KrcstToppageAlarmSet c ";
	/**
	 * convert from top page alarm set entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private TopPageAlarmSet toDomain(KrcstToppageAlarmSet entity){
		return TopPageAlarmSet.createFromJavaType(entity.companyId, entity.alarmCategory, entity.useAtr);
	}
	private KrcstToppageAlarmSet toEntity(TopPageAlarmSet domain){
		val entity = new KrcstToppageAlarmSet();
		entity.companyId = domain.getCompanyId();
		entity.alarmCategory = domain.getAlarmCategory().value;
		entity.useAtr = domain.getUseAtr().value;
		return entity;
	}
	/**
	 * find all item 
	 * @author yennth
	 */
	@Override
	public List<TopPageAlarmSet> getAll() {
		return this.queryProxy().query(SELECT_NO_WHERE, KrcstToppageAlarmSet.class).getList(c -> toDomain(c));
	}
	/**
	 * find by companyId
	 * @author yennth
	 */
	@Override
	public Optional<TopPageAlarmSet> getByCom(String companyId) {
		return this.queryProxy().find(companyId, KrcstToppageAlarmSet.class).map(x -> toDomain(x));
	}
	/**
	 * update a top page alarm set
	 * @author yennth
	 */
	@Override
	public void update(TopPageAlarmSet topPageAlarmSet) {
		this.commandProxy().update(toEntity(topPageAlarmSet));
	}

}
