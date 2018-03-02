package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrgForSyaRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetlPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgSetl;

/**
 * リポジトリ実装：社員の変形労働の精算期間
 * @author shuichu_ishida
 */
@Stateless
public class JpaSettlementPeriodOfIrgForSya extends JpaRepository implements SettlementPeriodOfIrgForSyaRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcstMonsetSyaRegAggr a "
			+ "WHERE a.PK.companyId = :companyId "
			+ "AND a.PK.employeeId = :employeeId ";
	
	/** 追加 */
	@Override
	public void insert(String companyId, String employeeId, SettlementPeriod settlementPeriod) {
		this.getEntityManager().persist(toEntity(companyId, employeeId, settlementPeriod, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String employeeId, SettlementPeriod settlementPeriod) {
		this.toEntity(companyId, employeeId, settlementPeriod, true);
	}
	
	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(String companyId, String employeeId) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param employeeId キー値：社員ID
	 * @param domain ドメイン：精算期間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社の変形労働の精算期間
	 */
	private KrcstMonsetSyaIrgSetl toEntity(String companyId, String employeeId, SettlementPeriod domain,
			boolean execUpdate){

		// キー
		val key = new KrcstMonsetSyaIrgSetlPK(companyId, employeeId, domain.getStartMonth().v());
		
		KrcstMonsetSyaIrgSetl entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcstMonsetSyaIrgSetl.class, key);
		}
		else {
			entity = new KrcstMonsetSyaIrgSetl();
			entity.PK = key;
			entity.setValue = new KrcstMonsetIrgSetl();
		}
		entity.setValue.endMonth = domain.getEndMonth().v();
		return entity;
	}
}
