package nts.uk.ctx.at.shared.dom.common.time;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.gul.util.range.RangeDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeSpanForCalcTest {

	@Test
	public void lengthAsMinutes() {
		
		val target = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(720));
		val actual = target.lengthAsMinutes();
		
		assertThat(actual, is(720));
	}

	@Test
	public void contains() {

		val target = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(720));

		assertThat("A", target.contains(new TimeWithDayAttr(-1)), is(false));
		assertThat("B", target.contains(new TimeWithDayAttr(0)), is(true));
		assertThat("C", target.contains(new TimeWithDayAttr(720)), is(true));
		assertThat("D", target.contains(new TimeWithDayAttr(721)), is(false));
		
		
		
		assertThat("C", target.contains(new TimeWithDayAttr(360)), is(true));
		assertThat("D", target.contains(new TimeWithDayAttr(-1)), is(false));
	}
	
	@Test
	public void timeDivide(){
		val target = new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(720));

		val actual1 = target.timeDivide(new TimeWithDayAttr(1));
		val expected1 = Arrays.asList(timeSpan(0, 1), timeSpan(1, 720));
		assertThat("basePoint:1",actual1, is(expected1));
	}
	
	@Test
	public void timeDivide_Limit(){
		val target = new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(720));

		val actual1 = target.timeDivide(new TimeWithDayAttr(0));
		val expected1 = Arrays.asList(timeSpan(0, 0), timeSpan(0, 720));
		assertThat("basePoint:0",actual1, is(expected1));

		val actual2 = target.timeDivide(new TimeWithDayAttr(720));
		val expected2 = Arrays.asList(timeSpan(0, 720), timeSpan(720, 720));
		assertThat("basePoint:720",actual2, is(expected2));	
	}
	
	@Test
	public void setClockAhead(){
		val target = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(720));
		
		val actual1 = target.shiftAhead(0);
		val expected1 = timeSpan(0, 720);
		assertThat("AheadMovement:0",actual1, is(expected1));
		
		val actual2 = target.shiftAhead(3000);
		val expected2 = timeSpan(3000, 3720);
		assertThat("AheadMovement:3000", actual2, is(expected2));
		
		val actual3 = target.shiftAhead(4319);
		val expected3 = timeSpan(4319, 4319);
		assertThat("AheadMovement:4319", actual3, is(expected3));
		
	}
	
	@Test
	public void setClockBack(){
		val target = new TimeSpanForCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(720));
		
		val actual1 = target.shiftBack(0);
		val expected1 =timeSpan(0, 720);
		assertThat("BackMovement:0",actual1, is(expected1));
		
		val actual2 = target.shiftBack(720);
		val expected2 = timeSpan(-720, 0);
		assertThat("BackMovement:-360",  actual2, is(expected2));
		
		val actual3 = target.shiftBack(2880);
		val expected3 = timeSpan(-720, -720);
		assertThat("BackMovement:-720",  actual3, is(expected3));
		
	}
	

	
	@Test
	public void rangeDuplication_case1()
	{
		Arrays.asList(
				new TestCaseForCompare("同じ期間", timeSpan(0,720), timeSpan(0,720), RangeDuplication.SAME_COMPLETE),
				new TestCaseForCompare("基準期間が完全に内包する", timeSpan(0,540), timeSpan(180,360), RangeDuplication.BASE_CONTAINS_COMPLETE),
				new TestCaseForCompare("基準期間が内包し、開始が同じ", timeSpan(0,720), timeSpan(0,540), RangeDuplication.BASE_CONTAINS_SAME_START),
				new TestCaseForCompare("基準期間が内包し、終了が同じ", timeSpan(0,720), timeSpan(360,720), RangeDuplication.BASE_CONTAINS_SAME_END),
				new TestCaseForCompare("基準期間が完全に内包される", timeSpan(180,540), timeSpan(0,720), RangeDuplication.BASE_CONTAINED_COMPLETE),
				new TestCaseForCompare("基準期間が内包され、開始が同じ", timeSpan(0,540), timeSpan(0,720), RangeDuplication.BASE_CONTAINED_SAME_START),
				new TestCaseForCompare("基準期間が内包され、終了が同じ", timeSpan(180,720), timeSpan(0,720), RangeDuplication.BASE_CONTAINED_SAME_END),
				new TestCaseForCompare("基準期間の後ろに連続する", timeSpan(0,360), timeSpan(360,720), RangeDuplication.CONTINUOUS_AFTER_BASE),
				new TestCaseForCompare("基準期間の前に連続する", timeSpan(360,720), timeSpan(0,360), RangeDuplication.CONTINUOUS_BEFORE_BASE),
				new TestCaseForCompare("基準期間が比較期間より前", timeSpan(0,180), timeSpan(540,720), RangeDuplication.BASE_BEFORE_COMPARED),
				new TestCaseForCompare("基準期間が比較期間より後", timeSpan(540,720), timeSpan(0,180), RangeDuplication.BASE_AFTER_COMPARED),
				new TestCaseForCompare("比較期間の開始が基準期間の間にある", timeSpan(0,540), timeSpan(180,720), RangeDuplication.START_SANDWITCHED_BETWEEN_BASE),
				new TestCaseForCompare("比較期間の終了が基準期間の間にある", timeSpan(360,720), timeSpan(180,540), RangeDuplication.END_SANDWITCHED_BETWEEN_BASE)				)
			.forEach(tc -> tc.test());
	}
	
	@Test
	public void rangeDuplication_case2(){
		val actual1 = timeSpan(1,180).compare(timeSpan(1,180));
		val expected1 = RangeDuplication.SAME_COMPLETE;
		assertThat("SAME_SPAN",actual1,is(expected1));
		val actual2 = timeSpan(0,180).compare(timeSpan(1,180));
		val expected2 = RangeDuplication.BASE_CONTAINS_SAME_END;
		assertThat("BASE_CONTAINS_SAME_END",actual2,is(expected2));
		val actual3 = timeSpan(2,180).compare(timeSpan(1,180));
		val expected3 = RangeDuplication.BASE_CONTAINED_SAME_END;
		assertThat("BASE_CONTAINED_SAME_END",actual3,is(expected3));
		
		val actual4 = timeSpan(180,540).compare(timeSpan(180,540));
		val expected4 = RangeDuplication.SAME_COMPLETE;
		assertThat("SAME_SPAN",actual4,is(expected4));
		val actual5 = timeSpan(180,539).compare(timeSpan(180,540));
		val expected5 = RangeDuplication.BASE_CONTAINED_SAME_START;
		assertThat("BASE_CONTAINED_SAME_START",actual5,is(expected5));
		val actual6 = timeSpan(180,541).compare(timeSpan(180,540));
		val expected6 = RangeDuplication.BASE_CONTAINS_SAME_START;
		assertThat("E1=E2+1",actual6,is(expected6));
	}
	
	
	@Test
	public void checkDuplication_case1() {
		/*開始時刻と終了時刻が一致している*/
		val target1_1 = timeSpan(0, 720);
		val target1_2 = timeSpan(0, 720);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.SAME_SPAN;
		assertThat("RangeDuplication:SAME_DUPLICATION", actual1,is(expected1));
	}
	
	@Test
	public void checkDuplication_case2() {
		/* 比較する時間帯が基準となる時間帯を完全に含んでいる*/
		val target1_1 = timeSpan(0, 360);
		val target1_2 = timeSpan(0, 720);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.CONTAINED;
		assertThat("CONTAINED", actual1,is(expected1));
		val target2_1 = timeSpan(360, 720);
		val target2_2 = timeSpan(0, 720);
		val actual2 = target2_1.checkDuplication(target2_2);
		val expected2 = TimeSpanDuplication.CONTAINED;
		assertThat("CONTAINED", actual2,is(expected2));
		val target3_1 = timeSpan(200, 400);
		val target3_2 = timeSpan(0, 720);
		val actual3 = target3_1.checkDuplication(target3_2);
		val expected3 = TimeSpanDuplication.CONTAINED;
		assertThat("CONTAINED", actual3,is(expected3));
	}
	
	@Test
	public void checkDuplication_case3() {
		/*基準となる時間帯が比較する時間帯を完全に含んでいる*/
		val target1_1 = timeSpan(0, 720);
		val target1_2 = timeSpan(0, 360);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.CONTAINS;
		assertThat("CONTAINS", actual1,is(expected1));
		val target2_1 = timeSpan(0, 720);
		val target2_2 = timeSpan(360, 720);
		val actual2 = target2_1.checkDuplication(target2_2);
		val expected2 = TimeSpanDuplication.CONTAINS;
		assertThat("CONTAINS", actual2,is(expected2));
		val target3_1 = timeSpan(0, 720);
		val target3_2 = timeSpan(180, 540);
		val actual3 = target3_1.checkDuplication(target3_2);
		val expected3 = TimeSpanDuplication.CONTAINS;
		assertThat("CONTAINS", actual3,is(expected3));
	}
	
	@Test
	public void checkDuplication_case4() {
		/*基準となる時間帯が比較する時間帯の終了時刻を跨いでいる*/
		val target1_1 = timeSpan(180, 540);
		val target1_2 = timeSpan(0, 360);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.CONNOTATE_ENDTIME;
		assertThat("CONNOTATE_ENDTIME", actual1,is(expected1));
	}
	
	@Test
	public void checkDuplication_case5() {
		/*基準となる時間帯が比較する時間帯の開始時刻を跨いでいる*/
		val target1_1 = timeSpan(180, 540);
		val target1_2 = timeSpan(360, 720);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.CONNOTATE_BEGINTIME;
		assertThat("CONNOTATE_BEGINTIME", actual1,is(expected1));
	}
	
	@Test
	public void checkDuplication_case6() {
		/*非重複*/
		val target1_1 = timeSpan(360, 720);
		val target1_2 = timeSpan(0, 360);
		val actual1 = target1_1.checkDuplication(target1_2);
		val expected1 = TimeSpanDuplication.NOT_DUPLICATE;
		assertThat("NOT_DUPLICATE", actual1,is(expected1));
		val target2_1 = timeSpan(0, 180);
		val target2_2 = timeSpan(360, 540);
		val actual2 = target2_1.checkDuplication(target2_2);
		val expected2 = TimeSpanDuplication.NOT_DUPLICATE;
		assertThat("NOT_DUPLICATE", actual2,is(expected2));
		val target3_1 = timeSpan(0, 360);
		val target3_2 = timeSpan(360, 720);
		val actual3 = target3_1.checkDuplication(target3_2);
		val expected3 = TimeSpanDuplication.NOT_DUPLICATE;
		assertThat("NOT_DUPLICATE", actual3,is(expected3));
		val target4_1 = timeSpan(360, 540);
		val target4_2 = timeSpan(0, 180);
		val actual4 = target4_1.checkDuplication(target4_2);
		val expected4 = TimeSpanDuplication.NOT_DUPLICATE;
		assertThat("NOT_DUPLICATE", actual4,is(expected4));
	}
	
	@Test
	public void reviseToAvoidDuplicatingWith_case1()
	{
		val target1_1 = timeSpan(180,360);
		val target1_2 = timeSpan(0,90);
		val actual1 = target1_1.reviseToAvoidDuplicatingWith(target1_2);
		val expected1 =  timeSpan(0,90);
		assertThat("test1",actual1,is(expected1));
		
		val target2_1 = timeSpan(180,360);
		val target2_2 = timeSpan(0,180);
		val actual2 = target2_1.reviseToAvoidDuplicatingWith(target2_2);
		val expected2 = timeSpan(0,180);
		assertThat("test2",actual2,is(expected2));
		
		val target3_1 = timeSpan(180,360);
		val target3_2 = timeSpan(90,270);
		val actual3 = target3_1.reviseToAvoidDuplicatingWith(target3_2);
		val expected3 =  timeSpan(360,540);
		assertThat("test3",actual3,is(expected3));
		
		val target4_1 = timeSpan(180,360);
		val target4_2 = timeSpan(180,270);
		val actual4 = target4_1.reviseToAvoidDuplicatingWith(target4_2);
		val expected4 =  timeSpan(360,450);
		assertThat("test4",actual4,is(expected4));

		val target5_1 = timeSpan(180,360);
		val target5_2 = timeSpan(240,300);
		val actual5 = target5_1.reviseToAvoidDuplicatingWith(target5_2);
		val expected5 =  timeSpan(360,420);
		assertThat("test5",actual5,is(expected5));
		
		val target6_1 = timeSpan(180,360);
		val target6_2 = timeSpan(270,360);
		val actual6 = target6_1.reviseToAvoidDuplicatingWith(target6_2);
		val expected6 =  timeSpan(360,450);
		assertThat("test6",actual6,is(expected6));
		
		val target7_1 = timeSpan(180,360);
		val target7_2 = timeSpan(270,450);
		val actual7 = target7_1.reviseToAvoidDuplicatingWith(target7_2);
		val expected7 =  timeSpan(360,540);
		assertThat("test7",actual7,is(expected7));
		
		val target8_1 = timeSpan(180,360);
		val target8_2 = timeSpan(450,540);
		val actual8 = target8_1.reviseToAvoidDuplicatingWith(target8_2);
		val expected8 =  timeSpan(450,540);
		assertThat("test8",actual8,is(expected8));
	}

	
	private static TimeSpanForCalc timeSpan(int start, int end) {
		return new TimeSpanForCalc(new TimeWithDayAttr(start),new TimeWithDayAttr(end));
	}
	
	
	@AllArgsConstructor
	private static class TestCaseForCompare {
		String summary;
		TimeSpanForCalc base;
		TimeSpanForCalc compared;
		RangeDuplication expected;
		
		public void test() {
			assertThat(summary, base.compare(compared), is(expected));
		}
	}
}
