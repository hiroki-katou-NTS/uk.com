package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class BreakTimeOfDailyAttdTest {
	
	@Test
	public void testIsDuplicatedWithBreakTime_true1 () {
		
		BreakTimeOfDailyAttd target = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(12, 0), 
							TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(15, 0), 
							TimeWithDayAttr.hourMinute(15, 30))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(12, 0), 
				TimeWithDayAttr.hourMinute(12, 30));
		
		boolean result = target.isDuplicatedWithBreakTime(other);
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsDuplicatedWithBreakTime_true2 () {
		
		BreakTimeOfDailyAttd target = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(12, 0), 
							TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(15, 0), 
							TimeWithDayAttr.hourMinute(15, 30))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(15, 0), 
				TimeWithDayAttr.hourMinute(16, 0));
		
		boolean result = target.isDuplicatedWithBreakTime(other);
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsDuplicatedWithBreakTime_true_allDuplicate () {
		
		BreakTimeOfDailyAttd target = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(12, 0), 
							TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(15, 0), 
							TimeWithDayAttr.hourMinute(15, 30))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(12, 0), 
				TimeWithDayAttr.hourMinute(16, 0));
		
		boolean result = target.isDuplicatedWithBreakTime(other);
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsDuplicatedWithBreakTime_false () {
		
		BreakTimeOfDailyAttd target = new BreakTimeOfDailyAttd(Arrays.asList(
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(12, 0), 
							TimeWithDayAttr.hourMinute(13, 0)),
				new BreakTimeSheet(
						new BreakFrameNo(1), 
							TimeWithDayAttr.hourMinute(15, 0), 
							TimeWithDayAttr.hourMinute(15, 30))
				));
		
		TimeSpanForCalc other = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(13, 0), 
				TimeWithDayAttr.hourMinute(14, 0));
		
		boolean result = target.isDuplicatedWithBreakTime(other);
		assertThat( result ).isFalse();
	}

}
