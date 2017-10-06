package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;

/**
 * 加給時間
 * @author keisuke_hoshina
 *
 */
@Value
public class BonusPayTime {
	private int BonusPayTimeItemNo;
	private TimeWithCalculation bonusPayTime;
	private TimeWithCalculation bonusPay;
	private TimeWithCalculation specifiedbonusPayTime;
}
