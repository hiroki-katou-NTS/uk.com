package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;

public class CompareRangeTest {

	@Test
	public void checkRange_BetweenClose_True() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_CLOSED);
		
		assertTrue(range.checkRange(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenClose_False() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenClose_False1() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenClose_False2() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(30d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenClose_False3() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(40d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenOpen_True() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_OPEN);
		
		assertTrue(range.checkRange(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenOpen_True1() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_OPEN);
		
		assertTrue(range.checkRange(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenOpen_False1() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_OPEN);
		
		assertTrue(!range.checkRange(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenOpen_True2() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_OPEN);
		
		assertTrue(range.checkRange(30d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_BetweenOpen_False3() {
		CompareRange<Integer> range = createRange(RangeCompareType.BETWEEN_RANGE_OPEN);
		
		assertTrue(!range.checkRange(40d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutClose_false() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutClose_false1() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutClose_True() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_CLOSED);
		
		assertTrue(range.checkRange(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutClose_false2() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_CLOSED);
		
		assertTrue(!range.checkRange(30d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutClose_True1() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_CLOSED);
		
		assertTrue(range.checkRange(40d, c -> Double.valueOf(c)));
	}
	

	
	@Test
	public void checkRange_OutOpen_false() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_OPEN);
		
		assertTrue(!range.checkRange(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutOpen_True() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_OPEN);
		
		assertTrue(range.checkRange(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutOpen_True2() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_OPEN);
		
		assertTrue(range.checkRange(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutOpen_True3() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_OPEN);
		
		assertTrue(range.checkRange(30d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkRange_OutOpen_True4() {
		CompareRange<Integer> range = createRange(RangeCompareType.OUTSIDE_RANGE_OPEN);
		
		assertTrue(range.checkRange(40d, c -> Double.valueOf(c)));
	}
	
	private CompareRange<Integer> createRange(RangeCompareType type){
		CompareRange<Integer> range = new CompareRange<>(type.value);
		range.setStartValue(10);
		range.setEndValue(30);
		return range;
	}

}
