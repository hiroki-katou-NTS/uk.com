package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 実働時間帯
 * @author keisuke_hoshina
 *
 */
public interface ActualWorkingTimeSheet {

	TimeWithDayAttr start();
	TimeWithDayAttr end();
}
