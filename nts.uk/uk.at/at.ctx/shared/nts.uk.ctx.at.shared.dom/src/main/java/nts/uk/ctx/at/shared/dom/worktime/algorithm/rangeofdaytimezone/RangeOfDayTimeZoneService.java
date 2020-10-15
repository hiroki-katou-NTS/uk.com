package nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 1日の範囲を時間帯で返す
 * 
 * @author trungtran
 *
 */
public interface RangeOfDayTimeZoneService {
	public TimeSpanForCalc getRangeofOneDay(String siftCode);

	public DuplicateStateAtr checkPeriodDuplication(TimeSpanForCalc destination, TimeSpanForCalc source);
	
	public DuplicationStatusOfTimeZone checkStateAtr(DuplicateStateAtr duplicateStateAtr);
	public void checkWithinRangeOfOneDay(DuplicationStatusOfTimeZone dStatusOfTimeZone,TimeSpanForCalc destination);
}
