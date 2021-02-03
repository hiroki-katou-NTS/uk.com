package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ErAlAttendanceItemConditionTest {

	@Test
	public void checkTarget_True() {
		ErAlAttendanceItemCondition<CheckedAmountValue> condition = createCondition(ConditionAtr.AMOUNT_VALUE, true, true, v -> new CheckedAmountValue(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False() {
		ErAlAttendanceItemCondition<CheckedAmountValue> condition = createCondition2(ConditionAtr.AMOUNT_VALUE, true, true, v -> new CheckedAmountValue(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True1() {
		ErAlAttendanceItemCondition<CheckedTimeDuration> condition = createCondition(ConditionAtr.TIME_DURATION, true, true, v -> new CheckedTimeDuration(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False1() {
		ErAlAttendanceItemCondition<CheckedTimeDuration> condition = createCondition2(ConditionAtr.TIME_DURATION, true, true, v -> new CheckedTimeDuration(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True3() {
		ErAlAttendanceItemCondition<TimeWithDayAttr> condition = createCondition(ConditionAtr.TIME_WITH_DAY, true, true, v -> new TimeWithDayAttr(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False3() {
		ErAlAttendanceItemCondition<TimeWithDayAttr> condition = createCondition2(ConditionAtr.TIME_WITH_DAY, true, true, v -> new TimeWithDayAttr(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True2() {
		ErAlAttendanceItemCondition<CheckedTimesValue> condition = createCondition(ConditionAtr.TIMES, true, true, v -> new CheckedTimesValue(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False2() {
		ErAlAttendanceItemCondition<CheckedTimesValue> condition = createCondition2(ConditionAtr.TIMES, true, true, v -> new CheckedTimesValue(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True4() {
		ErAlAttendanceItemCondition<CheckedAmountValue> condition = createCondition(ConditionAtr.AMOUNT_VALUE, false, false, v -> new CheckedAmountValue(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False4() {
		ErAlAttendanceItemCondition<CheckedAmountValue> condition = createCondition2(ConditionAtr.AMOUNT_VALUE, false, false, v -> new CheckedAmountValue(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True5() {
		ErAlAttendanceItemCondition<CheckedTimeDuration> condition = createCondition(ConditionAtr.TIME_DURATION, false, false, v -> new CheckedTimeDuration(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False5() {
		ErAlAttendanceItemCondition<CheckedTimeDuration> condition = createCondition2(ConditionAtr.TIME_DURATION, false, false, v -> new CheckedTimeDuration(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True6() {
		ErAlAttendanceItemCondition<TimeWithDayAttr> condition = createCondition(ConditionAtr.TIME_WITH_DAY, false, false, v -> new TimeWithDayAttr(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False6() {
		ErAlAttendanceItemCondition<TimeWithDayAttr> condition = createCondition2(ConditionAtr.TIME_WITH_DAY, false, false, v -> new TimeWithDayAttr(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}

	@Test
	public void checkTarget_True7() {
		ErAlAttendanceItemCondition<CheckedTimesValue> condition = createCondition(ConditionAtr.TIMES, false, false, v -> new CheckedTimesValue(v));
		assertTrue(condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	@Test
	public void checkTarget_False7() {
		ErAlAttendanceItemCondition<CheckedTimesValue> condition = createCondition2(ConditionAtr.TIMES, false, false, v -> new CheckedTimesValue(v));
		assertTrue(!condition.checkTarget(c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList())));
	}
	
	private <V> ErAlAttendanceItemCondition<V> createCondition(ConditionAtr type, boolean isRange, boolean calcAble,
			Function<Integer, V> toValue) {
		ErAlAttendanceItemCondition<V> condition = new ErAlAttendanceItemCondition<>("1", "1", 1, type.value,
				true, 0);
		if (isRange) {
			condition.setCompareRange(RangeCompareType.BETWEEN_RANGE_CLOSED.value, toValue.apply(1), toValue.apply(10));
		} else {
			condition.setCompareSingleValue(SingleValueCompareType.EQUAL.value, ConditionType.FIXED_VALUE.value, toValue.apply(5));
		}
		if (calcAble) {
			condition.setCountableTarget(Arrays.asList(1, 2, 3, 4), Arrays.asList(5));
		} else {
			condition.setUncountableTarget(5);
		}
		return condition;
	}
	
	private <V> ErAlAttendanceItemCondition<V> createCondition2(ConditionAtr type, boolean isRange, boolean calcAble,
			Function<Integer, V> toValue) {
		ErAlAttendanceItemCondition<V> condition = new ErAlAttendanceItemCondition<>("1", "1", 1, type.value,
				true, 0);
		if (isRange) {
			condition.setCompareRange(RangeCompareType.BETWEEN_RANGE_CLOSED.value, toValue.apply(1), toValue.apply(10));
		} else {
			condition.setCompareSingleValue(SingleValueCompareType.EQUAL.value, ConditionType.FIXED_VALUE.value, toValue.apply(5));
		}
		if (calcAble) {
			condition.setCountableTarget(Arrays.asList(1, 2, 3, 4), Arrays.asList(5, 5));
		} else {
			condition.setUncountableTarget(0);
		}
		return condition;
	}
}
