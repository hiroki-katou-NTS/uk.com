package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

import lombok.Getter;
import lombok.val;

/**
 * 集計時間設定
 * @author shuichu_ishida
 */
@Getter
public class AggregateTimeSet {

	/** 週割増・月割増を求める */
	private boolean askPremium;
	/** 1日の基準時間未満の残業時間の扱い */
	private TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay;
	/** 1週間の基準時間未満の休日出勤時間の扱い */
	private TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek;
	
	/**
	 * コンストラクタ
	 */
	public AggregateTimeSet(){
		
		this.treatOverTimeOfLessThanCriteriaPerDay = new TreatOverTimeOfLessThanCriteriaPerDay();
		this.treatHolidayWorkTimeOfLessThanCriteriaPerWeek = new TreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
	}

	/**
	 * ファクトリー
	 * @param askPremium 週割増・月割増を求める
	 * @param treatOverTimeOfLessThanCriteriaPerDay 1日の基準時間未満の残業時間の扱い
	 * @param treatHolidayWorkTimeOfLessThanCriteriaPerWeek 1週間の基準時間未満の休日出勤時間の扱い
	 * @return 集計時間設定
	 */
	public static AggregateTimeSet of(
			boolean askPremium,
			TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay,
			TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek){
		
		val domain = new AggregateTimeSet();
		domain.askPremium = askPremium;
		domain.treatOverTimeOfLessThanCriteriaPerDay = treatOverTimeOfLessThanCriteriaPerDay;
		domain.treatHolidayWorkTimeOfLessThanCriteriaPerWeek = treatHolidayWorkTimeOfLessThanCriteriaPerWeek;
		return domain;
	}
}
