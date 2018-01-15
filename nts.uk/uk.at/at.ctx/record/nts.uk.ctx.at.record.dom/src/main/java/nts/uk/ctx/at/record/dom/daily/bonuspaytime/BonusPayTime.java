package nts.uk.ctx.at.record.dom.daily.bonuspaytime;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;

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
