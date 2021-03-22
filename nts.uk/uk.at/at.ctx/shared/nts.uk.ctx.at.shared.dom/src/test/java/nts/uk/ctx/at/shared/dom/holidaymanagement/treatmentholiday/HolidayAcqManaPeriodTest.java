package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
/**
 * 
 * @author tutk
 *
 */
public class HolidayAcqManaPeriodTest {

	@Test
	public void testGetter() {
		HolidayAcqManaPeriod holidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1)),
				new FourWeekDays(4.0));
		NtsAssert.invokeGetters(holidayAcqManaPeriod);
	}
}
