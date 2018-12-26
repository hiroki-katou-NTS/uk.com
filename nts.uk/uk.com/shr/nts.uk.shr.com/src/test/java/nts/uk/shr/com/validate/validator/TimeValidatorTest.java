package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;

public class TimeValidatorTest {

	@Test
	public void testTimeTrue() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("1:30"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimeTrue1() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("01:30"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimeTrue2() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("30:25"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimeHourStyleFalse00() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType(":30"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeHourStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("aa:30"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeMinuteStyleFalse00() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("10:"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeMinuteStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("10:62"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeMinuteStyleFalse021() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("10:aa"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("0130"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeStyleFalse02() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("01h30m"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeMinFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("00:30"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}

	@Test
	public void testTimeMaxFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("11:30"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}

}
