package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrgForEmpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgSetlPK;

/**
 * リポジトリ実装：雇用の変形労働の精算期間
 * @author shuichu_ishida
 */
@Stateless
public class JpaSettlementPeriodOfIrgForEmp extends JpaRepository implements SettlementPeriodOfIrgForEmpRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcstMonsetEmpRegAggr a "
			+ "WHERE a.PK.companyId = :companyId "
			+ "AND a.PK.employmentCd = :employmentCd ";
	
	/** 追加 */
	@Override
	public void insert(String companyId, String employmentCd, SettlementPeriod settlementPeriod) {
		this.commandProxy().insert(toEntity(companyId, employmentCd, settlementPeriod, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String employmentCd, SettlementPeriod settlementPeriod) {
		this.toEntity(companyId, employmentCd, settlementPeriod, true);
	}
	
	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(String companyId, String employmentCd) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
			.setParameter("companyId", companyId)
			.setParameter("employmentCd", employmentCd)
			.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param employmentCd キー値：雇用コード
	 * @param domain ドメイン：精算期間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社の変形労働の精算期間
	 */
	private KrcstMonsetEmpIrgSetl toEntity(String companyId, String employmentCd, SettlementPeriod domain,
			boolean execUpdate){

		// キー
		val key = new KrcstMonsetEmpIrgSetlPK(companyId, employmentCd, domain.getStartMonth().v());
		
		KrcstMonsetEmpIrgSetl entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcstMonsetEmpIrgSetl.class).get();
		}
		else {
			entity = new KrcstMonsetEmpIrgSetl();
			entity.PK = key;
		}
		entity.setValue.endMonth = domain.getEndMonth().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
