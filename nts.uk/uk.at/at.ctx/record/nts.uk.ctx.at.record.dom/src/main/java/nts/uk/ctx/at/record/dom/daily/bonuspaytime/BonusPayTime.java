package nts.uk.ctx.at.record.dom.daily.bonuspaytime;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 加給時間
 * @author keisuke_hoshina
 *
 */
@Value
public class BonusPayTime {
	//加給項目ＮＯ
	private int BonusPayTimeItemNo;
	//加給時間
	private AttendanceTime bonusPayTime;
	//所定内加給
	private TimeWithCalculation withinBonusPay;
	//所定外加給
	private TimeWithCalculation excessBonusPayTime;
}
