package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrgForCmpRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetCmpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.company.KrcstMonsetCmpIrgAggr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * リポジトリ実装：会社の変形労働時間勤務の法定内集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaLegalAggrSetOfIrgForCmp extends JpaRepository implements LegalAggrSetOfIrgForCmpRepository {
	
	/** 更新 */
	@Override
	public void update(String companyId, LegalAggrSetOfIrg legalAggrSetOfIrg) {
		this.toUpdate(companyId, legalAggrSetOfIrg);
	}
	
	/**
	 * データ更新
	 * @param companyId キー値：会社ID
	 * @param domain ドメイン：変形労働時間勤務の法定内集計設定
	 */
	private void toUpdate(String companyId, LegalAggrSetOfIrg domain){
		
		// キー
		val key = new KrcstMonsetCmpRegAggrPK(companyId);
		
		// 集計時間設定
		val aggregateTimeSet = domain.getAggregateTimeSet();
		// 変形労働計算の設定
		val calcSetOfIrregular = domain.getCalcSetOfIrregular();
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay = aggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek = aggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		
		KrcstMonsetCmpIrgAggr entity = this.getEntityManager().find(KrcstMonsetCmpIrgAggr.class, key);
		if (entity == null) return;
		entity.setValue.toOverTimeWithinIrregularCriteria =
				(calcSetOfIrregular.isOverTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria() ? 1 : 0);
		entity.setValue.toWorkTimeOutsideCriteria =
				(calcSetOfIrregular.isWorkTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria() ? 1 : 0);
		
		for (int atrTreatOverTime = 1; atrTreatOverTime <= 2; atrTreatOverTime++){
			List<OverTimeFrameNo> overTimeFrameNoList =
					treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames();
			if (atrTreatOverTime == 2){
				overTimeFrameNoList = treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames();
			}
			for (val overTimeFrameNo : overTimeFrameNoList){
				switch(overTimeFrameNo.v().intValue()){
				case 1:
					entity.setValue.treatOverTime01 = atrTreatOverTime;
					break;
				case 2:
					entity.setValue.treatOverTime02 = atrTreatOverTime;
					break;
				case 3:
					entity.setValue.treatOverTime03 = atrTreatOverTime;
					break;
				case 4:
					entity.setValue.treatOverTime04 = atrTreatOverTime;
					break;
				case 5:
					entity.setValue.treatOverTime05 = atrTreatOverTime;
					break;
				case 6:
					entity.setValue.treatOverTime06 = atrTreatOverTime;
					break;
				case 7:
					entity.setValue.treatOverTime07 = atrTreatOverTime;
					break;
				case 8:
					entity.setValue.treatOverTime08 = atrTreatOverTime;
					break;
				case 9:
					entity.setValue.treatOverTime09 = atrTreatOverTime;
					break;
				case 10:
					entity.setValue.treatOverTime10 = atrTreatOverTime;
					break;
				}
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
	}
}
