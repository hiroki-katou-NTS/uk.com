package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.regularandirregular;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSetOfIrgForWkpRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetWkpRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.workplace.KrcstMonsetWkpIrgExot;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * リポジトリ実装：職場の変形労働時間勤務の時間外超過設定
 * @author shuichu_ishida
 */
@Stateless
public class JpaExcessOutsideTimeSetOfIrgForWkp extends JpaRepository implements ExcessOutsideTimeSetOfIrgForWkpRepository {
	
	/** 更新 */
	@Override
	public void update(String companyId, String workplaceId, ExcessOutsideTimeSet excessOutsideTimeSetting) {
		this.toUpdate(companyId, workplaceId, excessOutsideTimeSetting);
	}
	
	/**
	 * データ更新
	 * @param companyId キー値：会社ID
	 * @param workplaceId キー値：職場ID
	 * @param domain ドメイン：時間外超過設定
	 */
	private void toUpdate(String companyId, String workplaceId, ExcessOutsideTimeSet domain){

		// キー
		val key = new KrcstMonsetWkpRegAggrPK(companyId, workplaceId);
		
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay = domain.getTreatOverTimeOfLessThanCriteriaPerDay();
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek = domain.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		
		KrcstMonsetWkpIrgExot entity = this.getEntityManager().find(KrcstMonsetWkpIrgExot.class, key);
		if (entity == null) return;
		entity.setValue.exceptLegalHolidayWorkTime =
				(domain.isAutoExcludeHolidayWorkTimeFromExcessOutsideWorkTime() ? 1 : 0);
				
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
