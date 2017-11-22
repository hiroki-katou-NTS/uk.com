//package nts.uk.ctx.at.record.dom.daily.breaktimegoout;
//
//import static mockit.Deencapsulation.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//
//import org.junit.Test;
//
//import lombok.val;
//import nts.uk.ctx.at.shared.dom.common.time.TimeSpanTestUtil;
//
//public class BreakTimeSheetOfDailyTest {
//
//	@Test
//	public void sumBreakTimeIn() {
//		val target = new BreakTimeSheetOfDaily(null, Arrays.asList(
//				new BreakTimeSheet(TimeSpanTestUtil.create(30, 40), null, null, null),
//				new BreakTimeSheet(TimeSpanTestUtil.create(60, 80), null, null, null),
//				new BreakTimeSheet(TimeSpanTestUtil.create(100, 300), null, null, null)
//				));
//		
//		val actual = target.sumBreakTimeIn(TimeSpanTestUtil.create(0, 200));
//		assertThat(actual, is(130));
//	}
//
//}
