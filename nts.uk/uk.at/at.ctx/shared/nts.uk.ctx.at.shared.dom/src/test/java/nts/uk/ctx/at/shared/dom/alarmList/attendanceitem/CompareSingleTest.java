package nts.uk.ctx.at.shared.dom.alarmList.attendanceitem;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.alarmList.enums.ConditionType;
import nts.uk.ctx.at.shared.dom.alarmList.enums.SingleValueCompareType;
import org.junit.Assert;
import org.junit.Test;


public class CompareSingleTest {

	@Test
	public void checkWithFixedValue_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_EQUAL_False2() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_OR_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_OR_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_OR_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(!range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_THAN_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_THAN_False1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(!range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_GREATER_THAN_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(!range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_OR_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_OR_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_OR_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(!range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_THAN_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_THAN_False1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(!range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_LESS_THAN_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(!range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_NOT_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(9d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_NOT_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(range.checkWithFixedValue(11d, c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithFixedValue_NOT_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(!range.checkWithFixedValue(10d, c -> Double.valueOf(c)));
	}


	@Test
	public void checkWithAttendanceItem_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_EQUAL_False2() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_OR_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_OR_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_OR_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_OR_EQUAL);

		Assert.assertTrue(!range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_THAN_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_THAN_False1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(!range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_GREATER_THAN_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.GREATER_THAN);

		Assert.assertTrue(!range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_OR_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_OR_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_OR_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_OR_EQUAL);

		Assert.assertTrue(!range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_THAN_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_THAN_False1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(!range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_LESS_THAN_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.LESS_THAN);

		Assert.assertTrue(!range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_NOT_EQUAL_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(9d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_NOT_EQUAL_True1() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(range.checkWithAttendanceItem(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void checkWithAttendanceItem_NOT_EQUAL_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.NOT_EQUAL);

		Assert.assertTrue(!range.checkWithAttendanceItem(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void check_FIX_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.EQUAL);

		Assert.assertTrue(range.check(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void check_FIX_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.FIXED_VALUE, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.check(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void check_ATTENDANCE_ITEM_True() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.EQUAL);

		Assert.assertTrue(range.check(10d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	@Test
	public void check_ATTENDANCE_ITEM_False() {
		CompareSingleValue<Integer> range = createValue(ConditionType.ATTENDANCE_ITEM, SingleValueCompareType.EQUAL);

		Assert.assertTrue(!range.check(11d, c -> c.stream().map(x -> Double.valueOf(x)).collect(Collectors.toList()), c -> Double.valueOf(c)));
	}
	
	private CompareSingleValue<Integer> createValue(ConditionType type, SingleValueCompareType compareOpertor) {
		CompareSingleValue<Integer> value = new CompareSingleValue<>(compareOpertor.value, type.value);
		value.setValue(10);
		return value;
	}
}
