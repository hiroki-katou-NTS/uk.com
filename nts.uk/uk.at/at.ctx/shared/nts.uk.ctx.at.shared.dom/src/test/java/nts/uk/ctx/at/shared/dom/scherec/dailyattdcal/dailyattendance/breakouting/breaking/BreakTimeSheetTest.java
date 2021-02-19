package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class BreakTimeSheetTest {
	
	BreakTimeSheet target;
	
	@Before
	public void init() {
		target = new BreakTimeSheet(
				new BreakFrameNo(1), 
				TimeWithDayAttr.hourMinute(12, 0), 
				TimeWithDayAttr.hourMinute(13, 0));
	}
	
	@Test
	public void testGetter() {
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testConvertToTimeSpanForCalc() {
		
		TimeSpanForCalc result = target.convertToTimeSpanForCalc();
		
		assertThat( result.getStart().hour() ).isEqualTo( 12 );
		assertThat( result.getStart().minute() ).isEqualTo( 0 );
		assertThat( result.getEnd().hour() ).isEqualTo( 13 );
		assertThat( result.getEnd().minute() ).isEqualTo( 0 );
	}
	
}
