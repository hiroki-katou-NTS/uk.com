package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.flex;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlxForSyaRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaFlxAggr;

/**
 * リポジトリ実装：社員のフレックス時間勤務の月の集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaAggrSettingMonthlyOfFlxForSya extends JpaRepository implements AggrSettingMonthlyOfFlxForSyaRepository {

	/** 追加 */
	@Override
	public void insert(String companyId, String employeeId, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.commandProxy().insert(toEntity(companyId, employeeId, aggrSettingMonthlyOfFlx, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String employeeId, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.toEntity(companyId, employeeId, aggrSettingMonthlyOfFlx, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param employeeId キー値：社員ID
	 * @param domain ドメイン：フレックス時間勤務の月の集計設定
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社のフレックス時間勤務の月の集計設定
	 */
	private KrcstMonsetSyaFlxAggr toEntity(String companyId, String employeeId,
			AggrSettingMonthlyOfFlx domain, boolean execUpdate){
		
		// キー
		val key = new KrcstMonsetSyaRegAggrPK(companyId, employeeId);
		
		// フレックス不足設定
		val shortageSet = domain.getShortageSet();
		// 法定内集計設定
		val legalAggrSet = domain.getLegalAggregateSet();
		// 集計時間設定
		val aggrTimeSet = legalAggrSet.getAggregateTimeSet();
		// 時間外超過設定
		val excessOutsideTimeSet = legalAggrSet.getExcessOutsideTimeSet();
		// 36協定集計方法
		val aggrMethod36Agreement = domain.getArrgMethod36Agreement();
		
		KrcstMonsetSyaFlxAggr entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcstMonsetSyaFlxAggr.class).get();
		}
		else {
			entity = new KrcstMonsetSyaFlxAggr();
			entity.PK = key;
		}
		entity.setValue.aggregateMethod = domain.getAggregateMethod().value;
		entity.setValue.includeOverTime = (domain.isIncludeOverTime() ? 1 : 0);
		entity.setValue.carryforwardSet = shortageSet.getCarryforwardSet().value;
		entity.setValue.aggregateSet = aggrTimeSet.getAggregateSet().value;
		entity.setValue.excessOutsideTimeTargetSet = excessOutsideTimeSet.getExcessOutsideTimeTargetSet().value;
		entity.setValue.aggregateMethodOf36AgreementTime = aggrMethod36Agreement.getAggregateMethod().value;
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
