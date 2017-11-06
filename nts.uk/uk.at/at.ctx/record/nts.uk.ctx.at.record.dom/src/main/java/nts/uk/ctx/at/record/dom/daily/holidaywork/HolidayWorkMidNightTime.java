package nts.uk.ctx.at.record.dom.daily.holidaywork;
/**
 * 休出深夜時間
 * @author keisuke_hoshina
 *
 */

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

@Value
public class HolidayWorkMidNightTime {
	private TimeWithCalculation tiem;
	private StaturoryAtrOfHolidayWork statutoryAtr;
}
