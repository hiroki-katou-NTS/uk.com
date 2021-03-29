package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class MidNightTimeSheetTest {
	
	@Test
	public void getters() {
		MidNightTimeSheet target = new MidNightTimeSheet(
				"dummyCd",
				new TimeWithDayAttr(1320),	//22:00
				new TimeWithDayAttr(1740));	//29:00
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getAllMidNightTimeSheetTest() {
		MidNightTimeSheet midNight = new MidNightTimeSheet(
				"dummyCd",
				new TimeWithDayAttr(1320),	//22:00
				new TimeWithDayAttr(1740));	//29:00
		assertThat(midNight.getAllMidNightTimeSheet())
				.extracting(
						t -> t.getSpan())
				.containsExactly(
						new TimeSpanForCalc(new TimeWithDayAttr(-120), new TimeWithDayAttr(300)),	//-2:00~5:00
						new TimeSpanForCalc(new TimeWithDayAttr(1320), new TimeWithDayAttr(1740)),	//22:00~29:00
						new TimeSpanForCalc(new TimeWithDayAttr(2760), new TimeWithDayAttr(3180)),	//46:00~53:00
						new TimeSpanForCalc(new TimeWithDayAttr(4200), new TimeWithDayAttr(4319)));	//70:00~71:59
	}

}
