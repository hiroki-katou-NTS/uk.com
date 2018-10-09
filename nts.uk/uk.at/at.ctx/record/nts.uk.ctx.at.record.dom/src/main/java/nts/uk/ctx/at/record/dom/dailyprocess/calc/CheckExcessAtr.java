package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 実績超過を判定したい項目
 * @author keisuke_hoshina
 *
 */
public enum CheckExcessAtr {
	//遅刻
	 LATE,
	//早退
	 LEAVE_EARLY,
	//事前残業申請超過
	 PRE_OVERTIME_APP_EXCESS,
	//事前休出申請超過
	 PRE_HOLIDAYWORK_APP_EXCESS,
	//事前フレックス申請超過
	 PRE_FLEX_APP_EXCESS,
	//事前深夜申請超過
	 PRE_MIDNIGHT_EXCESS,
	//残業時間実績超過
	 OVER_TIME_EXCESS,
	//休出時間実績超過
	 REST_TIME_EXCESS,
	//フレックス時間実績超過
	 FLEX_OVER_TIME,
	//深夜時間実績超過
	 MIDNIGHT_EXCESS,
	
	//乖離時間のエラー	
	 ERROR_OF_DIVERGENCE_TIME,
	//乖離時間のアラーム
	 ALARM_OF_DIVERGENCE_TIME;
}
