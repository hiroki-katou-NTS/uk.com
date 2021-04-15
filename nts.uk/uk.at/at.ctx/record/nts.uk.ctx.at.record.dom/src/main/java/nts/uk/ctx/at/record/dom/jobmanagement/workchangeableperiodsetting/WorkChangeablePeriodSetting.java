package nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author thanhpv
 * @name 作業変更可能期間設定
 * @path UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.作業変更可能期間設定.作業変更可能期間設定
 */
@Getter
@AllArgsConstructor
public class WorkChangeablePeriodSetting extends AggregateRoot {
	
	/** 経過月数 */
	private MonthsAgo monthsAgo;
//■Public
	/**
	 * @name [1] 作業修正可能開始日付を取得する
	 * @Decription 	説明:指定社員の締め開始日から作業修正可能期間の開始日付を求める
	 * @input require
	 * @input 社員ID
	 * @output 修正可能開始日付 GeneralDate
	 */
	public GeneralDate getWorkCorrectionStartDate(Require require, String employeeId) {
		//$作業変更可能期間設定 = require.作業変更可能期間設定を取得する()
		WorkChangeablePeriodSetting workChangeablePeriodSetting = require.get();
		//$期間 = require.締め期間を取得する(社員ID, 年月日#今日())
		DatePeriod period = require.getPeriod(employeeId, GeneralDate.today());
		switch (workChangeablePeriodSetting.getMonthsAgo()) {
			case ONE_MONTH_AGO:
				return period.start().addMonths(-1);
			case TOW_MONTH_AGO:
				return period.start().addMonths(-2);
			case THREE_MONTH_AGO:
				return period.start().addMonths(-3);
			case FOUR_MONTH_AGO:
				return period.start().addMonths(-4);
			case FIVE_MONTH_AGO:
				return period.start().addMonths(-5);
			default:
				return period.start();
		}
	}
	
//■Require
	public static interface Require {
		//[R-1] 作業変更可能期間設定を取得する
		//作業変更可能期間設定Repository.get(会社ID)	
		WorkChangeablePeriodSetting get();
		
		//[R-2] 締め期間を取得する
		//アルゴリズム.社員に対応する締め期間を取得する(社員ID,基準日)
		DatePeriod getPeriod(String employeeId, GeneralDate date);
	}
}
