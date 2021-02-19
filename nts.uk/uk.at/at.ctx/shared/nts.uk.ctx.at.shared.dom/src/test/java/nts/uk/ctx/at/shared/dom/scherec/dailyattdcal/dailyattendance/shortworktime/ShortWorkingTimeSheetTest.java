package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortWorkingTimeSheetTest {
	
	@Test
	public void testGetters() {
		
		ShortWorkingTimeSheet target = new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(1), 
				ChildCareAttribute.CARE, 
				TimeWithDayAttr.hourMinute(15, 0), 
				TimeWithDayAttr.hourMinute(17, 0));
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testConvertToTimeSpanForCalc() {
		
		ShortWorkingTimeSheet target = new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(1), 
				ChildCareAttribute.CARE, 
				TimeWithDayAttr.hourMinute(15, 0), 
				TimeWithDayAttr.hourMinute(17, 0));
		
		TimeSpanForCalc result = target.convertToTimeSpanForCalc();
		
		assertThat( result.getStart().hour() ).isEqualTo( 15 );
		assertThat( result.getEnd().hour() ).isEqualTo( 17 );
	}
	
}
