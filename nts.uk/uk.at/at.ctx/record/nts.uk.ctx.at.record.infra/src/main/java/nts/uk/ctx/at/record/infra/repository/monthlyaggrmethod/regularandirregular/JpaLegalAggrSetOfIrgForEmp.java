package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrgForEmpRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetEmpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment.KrcstMonsetEmpIrgAggr;

/**
 * リポジトリ実装：雇用の変形労働時間勤務の法定内集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaLegalAggrSetOfIrgForEmp extends JpaRepository implements LegalAggrSetOfIrgForEmpRepository {

	/** 追加 */
	@Override
	public void insert(String companyId, String employmentCd, LegalAggrSetOfIrg legalAggrSetOfIrg) {
		this.commandProxy().insert(toEntity(companyId, employmentCd, legalAggrSetOfIrg, false));
	}
	
	/** 更新 */
	@Override
	public void update(String companyId, String employmentCd, LegalAggrSetOfIrg legalAggrSetOfIrg) {
		this.toEntity(companyId, employmentCd, legalAggrSetOfIrg, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param companyId キー値：会社ID
	 * @param employmentCd キー値：雇用コード
	 * @param domain ドメイン：変形労働時間勤務の法定内集計設定
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：会社の変形労働時間勤務の月の集計設定
	 */
	private KrcstMonsetEmpIrgAggr toEntity(String companyId, String employmentCd,
			LegalAggrSetOfIrg domain, boolean execUpdate){
		
		// キー
		val key = new KrcstMonsetEmpRegAggrPK(companyId, employmentCd);
		
		// 集計時間設定
		val aggregateTimeSet = domain.getAggregateTimeSet();
		// 変形労働計算の設定
		val calcSetOfIrregular = domain.getCalcSetOfIrregular();
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay = aggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek = aggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		
		KrcstMonsetEmpIrgAggr entity;
		if (execUpdate) {
			entity = this.queryProxy().find(key, KrcstMonsetEmpIrgAggr.class).get();
		}
		else {
			entity = new KrcstMonsetEmpIrgAggr();
			entity.PK = key;
		}
		entity.setValue.toOverTimeWithinIrregularCriteria =
				(calcSetOfIrregular.isOverTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria() ? 1 : 0);
		entity.setValue.toWorkTimeOutsideCriteria =
				(calcSetOfIrregular.isWorkTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria() ? 1 : 0);
		
		int atrAutoExcludeOverTime = 1;
		for (val autoExcludeOverTimeFrame : treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames()){
			switch(autoExcludeOverTimeFrame.v().intValue()){
			case 1:
				entity.setValue.treatOverTime01 = atrAutoExcludeOverTime;
				break;
			case 2:
				entity.setValue.treatOverTime02 = atrAutoExcludeOverTime;
				break;
			case 3:
				entity.setValue.treatOverTime03 = atrAutoExcludeOverTime;
				break;
			case 4:
				entity.setValue.treatOverTime04 = atrAutoExcludeOverTime;
				break;
			case 5:
				entity.setValue.treatOverTime05 = atrAutoExcludeOverTime;
				break;
			case 6:
				entity.setValue.treatOverTime06 = atrAutoExcludeOverTime;
				break;
			case 7:
				entity.setValue.treatOverTime07 = atrAutoExcludeOverTime;
				break;
			case 8:
				entity.setValue.treatOverTime08 = atrAutoExcludeOverTime;
				break;
			case 9:
				entity.setValue.treatOverTime09 = atrAutoExcludeOverTime;
				break;
			case 10:
				entity.setValue.treatOverTime10 = atrAutoExcludeOverTime;
				break;
			}
		}
		
		int atrLegalOverTime = 2;
		for (val legalOverTimeFrame : treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames()){
			switch(legalOverTimeFrame.v().intValue()){
			case 1:
				entity.setValue.treatOverTime01 = atrLegalOverTime;
				break;
			case 2:
				entity.setValue.treatOverTime02 = atrLegalOverTime;
				break;
			case 3:
				entity.setValue.treatOverTime03 = atrLegalOverTime;
				break;
			case 4:
				entity.setValue.treatOverTime04 = atrLegalOverTime;
				break;
			case 5:
				entity.setValue.treatOverTime05 = atrLegalOverTime;
				break;
			case 6:
				entity.setValue.treatOverTime06 = atrLegalOverTime;
				break;
			case 7:
				entity.setValue.treatOverTime07 = atrLegalOverTime;
				break;
			case 8:
				entity.setValue.treatOverTime08 = atrLegalOverTime;
				break;
			case 9:
				entity.setValue.treatOverTime09 = atrLegalOverTime;
				break;
			case 10:
				entity.setValue.treatOverTime10 = atrLegalOverTime;
				break;
			}
		}
		
		int atrAutoExcludeHolidayWork = 1;
		for (val autoExcludeHolidayWorkFrame : treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames()){
			switch(autoExcludeHolidayWorkFrame.v().intValue()){
			case 1:
				entity.setValue.treatHolidayWorkTime01 = atrAutoExcludeHolidayWork;
				break;
			case 2:
				entity.setValue.treatHolidayWorkTime02 = atrAutoExcludeHolidayWork;
				break;
			case 3:
				entity.setValue.treatHolidayWorkTime03 = atrAutoExcludeHolidayWork;
				break;
			case 4:
				entity.setValue.treatHolidayWorkTime04 = atrAutoExcludeHolidayWork;
				break;
			case 5:
				entity.setValue.treatHolidayWorkTime05 = atrAutoExcludeHolidayWork;
				break;
			case 6:
				entity.setValue.treatHolidayWorkTime06 = atrAutoExcludeHolidayWork;
				break;
			case 7:
				entity.setValue.treatHolidayWorkTime07 = atrAutoExcludeHolidayWork;
				break;
			case 8:
				entity.setValue.treatHolidayWorkTime08 = atrAutoExcludeHolidayWork;
				break;
			case 9:
				entity.setValue.treatHolidayWorkTime09 = atrAutoExcludeHolidayWork;
				break;
			case 10:
				entity.setValue.treatHolidayWorkTime10 = atrAutoExcludeHolidayWork;
				break;
			}
		}
		
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
