package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.flex;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlxForCmpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetCmpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpFlxAggr;

/**
 * リポジトリ実装：会社のフレックス時間勤務の月の集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaAggrSettingMonthlyOfFlxForCmp extends JpaRepository implements AggrSettingMonthlyOfFlxForCmpRepository {

	/** 追加 */
	@Override
	public void insert(String companyId, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.commandProxy().insert(toEntity(companyId, aggrSettingMonthlyOfFlx, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx) {
		this.toEntity(companyId, aggrSettingMonthlyOfFlx, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param domain ドメイン：フレックス時間勤務の月の集計設定
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社のフレックス時間勤務の月の集計設定
	 */
	private KrcstMonsetCmpFlxAggr toEntity(String companyId,
			AggrSettingMonthlyOfFlx domain, boolean execUpdate){
		
		// キー
		val key = new KrcstMonsetCmpRegAggrPK(companyId);
		
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
		
		KrcstMonsetCmpFlxAggr entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcstMonsetCmpFlxAggr.class).get();
		}
		else {
			entity = new KrcstMonsetCmpFlxAggr();
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
