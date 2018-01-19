package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import lombok.Getter;
import lombok.val;

/**
 * 変形労働計算の設定
 * @author shuichu_ishida
 */
@Getter
public class CalcSettingOfIrregular {

	/** 基準時間未満の残業時間を変形基準内残業とする */
	private boolean overTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria;
	/** 所定もしくは基準時間を超えた就業時間を基準外就業時間とする */
	private boolean workTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria;

	/**
	 * ファクトリー
	 * @param overTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria 基準時間未満の残業時間を変形基準内残業とする
	 * @param workTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria 所定もしくは基準時間を超えた就業時間を基準外就業時間とする
	 * @return 変形労働計算の設定
	 */
	public static CalcSettingOfIrregular of(
			boolean overTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria,
			boolean workTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria){
		
		val domain = new CalcSettingOfIrregular();
		domain.overTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria = overTimeLessThanCriteriaIsOverTimeWithinIrregularCriteria;
		domain.workTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria = workTimeMoreThanPrescribedOrCriteriaIsWorkTimeOutsideCriteria;
		return domain;
	}
}
