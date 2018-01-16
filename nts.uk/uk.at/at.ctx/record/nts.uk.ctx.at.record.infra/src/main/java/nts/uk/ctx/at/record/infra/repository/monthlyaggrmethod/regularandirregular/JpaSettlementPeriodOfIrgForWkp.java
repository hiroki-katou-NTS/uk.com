package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrgForWkpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgSetlPK;

/**
 * リポジトリ実装：職場の変形労働の精算期間
 * @author shuichu_ishida
 */
@Stateless
public class JpaSettlementPeriodOfIrgForWkp extends JpaRepository implements SettlementPeriodOfIrgForWkpRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcstMonsetWkpRegAggr a "
			+ "WHERE a.PK.companyId = :companyId "
			+ "AND a.PK.workplaceId = :workplaceId ";
	
	/** 追加 */
	@Override
	public void insert(String companyId, String workplaceId, SettlementPeriod settlementPeriod) {
		this.commandProxy().insert(toEntity(companyId, workplaceId, settlementPeriod, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String workplaceId, SettlementPeriod settlementPeriod) {
		this.toEntity(companyId, workplaceId, settlementPeriod, true);
	}
	
	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(String companyId, String workplaceId) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
			.setParameter("companyId", companyId)
			.setParameter("workplaceId", workplaceId)
			.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param workplaceId キー値：職場ID
	 * @param domain ドメイン：精算期間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社の変形労働の精算期間
	 */
	private KrcstMonsetWkpIrgSetl toEntity(String companyId, String workplaceId, SettlementPeriod domain,
			boolean execUpdate){

		// キー
		val key = new KrcstMonsetWkpIrgSetlPK(companyId, workplaceId, domain.getStartMonth().v());
		
		KrcstMonsetWkpIrgSetl entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcstMonsetWkpIrgSetl.class).get();
		}
		else {
			entity = new KrcstMonsetWkpIrgSetl();
			entity.PK = key;
		}
		entity.setValue.endMonth = domain.getEndMonth().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
