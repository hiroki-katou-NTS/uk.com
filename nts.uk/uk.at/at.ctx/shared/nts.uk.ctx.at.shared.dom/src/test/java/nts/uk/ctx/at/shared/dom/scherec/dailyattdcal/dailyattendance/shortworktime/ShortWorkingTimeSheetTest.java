package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortWorkingTimeSheetTest {
	
	ShortWorkingTimeSheet target;
	
	@Before
	public void init() {
		target = new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(1), 
				ChildCareAtr.CARE, 
				TimeWithDayAttr.hourMinute(15, 30), 
				TimeWithDayAttr.hourMinute(17, 0));
	}
	
	@Test
	public void testGetters() {
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testConvertToTimeSpanForCalc() {
		
		TimeSpanForCalc result = target.convertToTimeSpanForCalc();
		
		assertThat( result.getStart().hour() ).isEqualTo( 15 );
		assertThat( result.getStart().minute() ).isEqualTo( 30 );
		assertThat( result.getEnd().hour() ).isEqualTo( 17 );
		assertThat( result.getEnd().minute() ).isEqualTo( 0 );
	}
	
}
