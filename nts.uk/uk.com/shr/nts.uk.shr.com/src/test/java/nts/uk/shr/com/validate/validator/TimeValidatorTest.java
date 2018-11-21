package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;

public class TimeValidatorTest {

	@Test
	public void testTimeTrue() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "1:30");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimeTrue1() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "01:30");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTimeTrue2() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 6030);
		Optional<String> result = TimeValidator.validate(constraint, "30:25");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimeHourStyleFalse00() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, ":30");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeHourStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "aa:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeMinuteStyleFalse00() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "10:");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeMinuteStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "10:62");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeMinuteStyleFalse021() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "10:aa");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimeStyleFalse01() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "0130");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeStyleFalse02() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "01h30m");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimeMinFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "00:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}

	@Test
	public void testTimeMaxFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "11:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}

}
