package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.flex;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlxForEmpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetEmpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpFlxAggr;

/**
 * リポジトリ実装：雇用のフレックス時間勤務の月の集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaAggrSettingMonthlyOfFlxForEmp extends JpaRepository implements AggrSettingMonthlyOfFlxForEmpRepository {

	/** 追加 */
	@Override
	public void insert(String companyId, String employmentCode, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.commandProxy().insert(toEntity(companyId, employmentCode, aggrSettingMonthlyOfFlx, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String employmentCode, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.toEntity(companyId, employmentCode, aggrSettingMonthlyOfFlx, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param employmentCode キー値：雇用コード
	 * @param domain ドメイン：フレックス時間勤務の月の集計設定
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社のフレックス時間勤務の月の集計設定
	 */
	private KrcstMonsetEmpFlxAggr toEntity(String companyId, String employmentCode,
			AggrSettingMonthlyOfFlx domain, boolean execUpdate){
		
		// キー
		val key = new KrcstMonsetEmpRegAggrPK(companyId, employmentCode);
		
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
		
		KrcstMonsetEmpFlxAggr entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcstMonsetEmpFlxAggr.class).get();
		}
		else {
			entity = new KrcstMonsetEmpFlxAggr();
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
