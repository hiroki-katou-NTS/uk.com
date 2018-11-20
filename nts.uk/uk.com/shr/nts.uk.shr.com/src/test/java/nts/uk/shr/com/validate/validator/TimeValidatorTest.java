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
	public void testTimeStyleFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "01:62");
		Assert.assertEquals(result.get(), ErrorIdFactory.getTimeStyleErrorId());
	}

	@Test
	public void testTimeStyleFalse1() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "0130");
		Assert.assertEquals(result.get(), ErrorIdFactory.getTimeStyleErrorId());
	}
	
	@Test
	public void testTimeStyleFalse2() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, ":30");
		Assert.assertEquals(result.get(), ErrorIdFactory.getTimeStyleErrorId());
	}

	@Test
	public void testTimeMinFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "00:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.getTimeMinErrorId());
	}

	@Test
	public void testTimeMaxFalse() {
		TimeConstraint constraint = new TimeConstraint(1, 60, 600);
		Optional<String> result = TimeValidator.validate(constraint, "11:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.getTimeMaxErrorId());
	}

}
