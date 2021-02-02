package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortWorkingTimeSheetTest {
	
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
	
	
	@Test
	public void testIsDuplicatedWithTimeSpan_true() {
		
		ShortWorkingTimeSheet target = new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(1), 
				ChildCareAttribute.CARE, 
				TimeWithDayAttr.hourMinute(15, 0), 
				TimeWithDayAttr.hourMinute(17, 0));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(16, 0), 
				TimeWithDayAttr.hourMinute(18, 0));
		
		boolean result = target.isDuplicatedWithTimeSpan(other);
		
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsDuplicatedWithTimeSpan_false() {
		
		ShortWorkingTimeSheet target = new ShortWorkingTimeSheet(
				new ShortWorkTimFrameNo(1), 
				ChildCareAttribute.CARE, 
				TimeWithDayAttr.hourMinute(15, 0), 
				TimeWithDayAttr.hourMinute(17, 0));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(13, 0), 
				TimeWithDayAttr.hourMinute(14, 0));
		
		boolean result = target.isDuplicatedWithTimeSpan(other);
		
		assertThat( result ).isFalse();
	}
	
}
