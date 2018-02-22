package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrgForCmpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgSetlPK;

/**
 * リポジトリ実装：会社の変形労働の精算期間
 * @author shuichu_ishida
 */
@Stateless
public class JpaSettlementPeriodOfIrgForCmp extends JpaRepository implements SettlementPeriodOfIrgForCmpRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcstMonsetCmpRegAggr a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	/** 追加 */
	@Override
	public void insert(String companyId, SettlementPeriod settlementPeriod) {
		this.getEntityManager().persist(toEntity(companyId, settlementPeriod, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, SettlementPeriod settlementPeriod) {
		this.toEntity(companyId, settlementPeriod, true);
	}
	
	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(String companyId) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
				.setParameter("companyId", companyId)
				.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param domain ドメイン：精算期間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社の変形労働の精算期間
	 */
	private KrcstMonsetCmpIrgSetl toEntity(String companyId, SettlementPeriod domain,
			boolean execUpdate){

		// キー
		val key = new KrcstMonsetCmpIrgSetlPK(companyId, domain.getStartMonth().v());
		
		KrcstMonsetCmpIrgSetl entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcstMonsetCmpIrgSetl.class, key);
		}
		else {
			entity = new KrcstMonsetCmpIrgSetl();
			entity.PK = key;
		}
		entity.setValue.endMonth = domain.getEndMonth().v();
		return entity;
	}
}
