package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import lombok.Getter;
import lombok.val;

/**
 * 時間外超過設定
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideTimeSet {

	/** 週割増・月割増を求める */
	private boolean askPremium;
	/** 勤務種類が法内休出の休出時間は時間外超過対象から自動的に除く */
	private boolean autoExcludeHolidayWorkTimeFromExcessOutsideWorkTime;
	/** 1日の基準時間未満の残業時間の扱い */
	private TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay;
	/** 1週間の基準時間未満の休日出勤時間の扱い */
	private TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideTimeSet(){
		
		this.treatOverTimeOfLessThanCriteriaPerDay = new TreatOverTimeOfLessThanCriteriaPerDay();
		this.treatHolidayWorkTimeOfLessThanCriteriaPerWeek = new TreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
	}

	/**
	 * ファクトリー
	 * @param askPremium 週割増・月割増を求める
	 * @param autoExcludeHolidayWorkTimeFromExcessOutsideWorkTime 勤務種類が法内休出の休出時間は時間外超過対象から自動的に除く
	 * @param treatOverTimeOfLessThanCriteriaPerDay 1日の基準時間未満の残業時間の扱い
	 * @param treatHolidayWorkTimeOfLessThanCriteriaPerWeek 1週間の基準時間未満の休日出勤時間の扱い
	 * @return 時間外超過設定
	 */
	public static ExcessOutsideTimeSet of(
			boolean askPremium,
			boolean autoExcludeHolidayWorkTimeFromExcessOutsideWorkTime,
			TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay,
			TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek){

		val domain = new ExcessOutsideTimeSet();
		domain.askPremium = askPremium;
		domain.autoExcludeHolidayWorkTimeFromExcessOutsideWorkTime = autoExcludeHolidayWorkTimeFromExcessOutsideWorkTime;
		domain.treatOverTimeOfLessThanCriteriaPerDay = treatOverTimeOfLessThanCriteriaPerDay;
		domain.treatHolidayWorkTimeOfLessThanCriteriaPerWeek = treatHolidayWorkTimeOfLessThanCriteriaPerWeek;
		return domain;
	}
}
