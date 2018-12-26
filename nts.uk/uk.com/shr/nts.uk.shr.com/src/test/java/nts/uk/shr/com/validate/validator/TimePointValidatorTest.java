package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.implement.TimePointConstraint;

public class TimePointValidatorTest {

	@Test
	public void testTimePointTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("1:30"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 600);
		Optional<String> result = constraint.validate(new ValueWithType("01:30"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointStyleFalse00() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("30:25"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse01() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType(":25"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse02() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("aa:01"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse10() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("01:65"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse11() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("00:aa"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse12() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("10:"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse20() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("1001"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointStyleFalse30() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("001:001"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointMinTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("01:01"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointMinTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("01:00"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointMinFalse() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("00:30"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}

	@Test
	public void testTimePointMinFalse1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = constraint.validate(new ValueWithType("00:29"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}

	@Test
	public void testTimePointMaxTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = constraint.validate(new ValueWithType("10:29"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointMaxTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = constraint.validate(new ValueWithType("10:30"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimePointMaxFalse() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = constraint.validate(new ValueWithType("10:31"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}

	@Test
	public void testTimePointMaxFalse1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = constraint.validate(new ValueWithType("10:35"));
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}

}
