package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import lombok.AllArgsConstructor;

/**
 * 36協定期間
 * @author yennth
 *
 */
@AllArgsConstructor
public enum Period {
	/** 0: 1週間 (1Week) */
	One_Week(0),
	/** 1: 2週間 (2week) */
	Two_Week(1),
	/** 2: 4週間 (4week) */
	Four_Week(2),
	/** 3: 1ヶ月 (1Month) */
	One_Month(3),
	/** 4: 2ヶ月 (2Month) */
	Two_Month(4),
	/** 5: 3ヶ月 (3Month) */
	Three_Month(5),
	/** 6: 年間 (Yearly) */
	Yearly(6);
	public final int value;
}
