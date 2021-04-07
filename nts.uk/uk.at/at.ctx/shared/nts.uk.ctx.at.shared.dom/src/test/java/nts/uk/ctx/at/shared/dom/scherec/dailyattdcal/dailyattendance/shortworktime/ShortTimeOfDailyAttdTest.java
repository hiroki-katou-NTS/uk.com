package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortTimeOfDailyAttdTest {
	
	@Test
	public void testIsDuplicatedWithShortTime_true1() {
		
		ShortTimeOfDailyAttd target = new ShortTimeOfDailyAttd( Arrays.asList( 
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(1), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)),
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(16, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(14, 30), 
				TimeWithDayAttr.hourMinute(15, 30));
		
		boolean result = target.isDuplicatedWithShortTime( other );
		
		assertThat( result ).isTrue();
	} 
	
	@Test
	public void testIsDuplicatedWithShortTime_true2() {
		
		ShortTimeOfDailyAttd target = new ShortTimeOfDailyAttd( Arrays.asList( 
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(1), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)),
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(16, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(16, 30), 
				TimeWithDayAttr.hourMinute(17, 30));
		
		boolean result = target.isDuplicatedWithShortTime( other );
		
		assertThat( result ).isTrue();
	} 
	
	@Test
	public void testIsDuplicatedWithShortTime_true_bothDuplicated() {
		
		ShortTimeOfDailyAttd target = new ShortTimeOfDailyAttd( Arrays.asList( 
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(1), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)),
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(16, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(14, 30), 
				TimeWithDayAttr.hourMinute(17, 30));
		
		boolean result = target.isDuplicatedWithShortTime( other );
		
		assertThat( result ).isTrue();
	} 
	
	@Test
	public void testIsDuplicatedWithShortTime_false() {
		
		ShortTimeOfDailyAttd target = new ShortTimeOfDailyAttd( Arrays.asList( 
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(1), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)),
				new ShortWorkingTimeSheet(
						new ShortWorkTimFrameNo(2), 
						ChildCareAttribute.CARE, 
						TimeWithDayAttr.hourMinute(16, 0), 
						TimeWithDayAttr.hourMinute(17, 0))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(8, 0), 
				TimeWithDayAttr.hourMinute(9, 0));
		
		boolean result = target.isDuplicatedWithShortTime( other );
		
		assertThat( result ).isFalse();
	}

}
