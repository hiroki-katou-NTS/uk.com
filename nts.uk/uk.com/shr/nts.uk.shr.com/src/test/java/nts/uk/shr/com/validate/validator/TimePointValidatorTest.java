package nts.uk.shr.com.validate.validator;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.shr.com.validate.constraint.implement.TimePointConstraint;

public class TimePointValidatorTest {

	@Test
	public void testTimePointTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 600);
		Optional<String> result = TimePointValidator.validate(constraint, "1:30");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 600);
		Optional<String> result = TimePointValidator.validate(constraint, "01:30");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointStyleFalse() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "30:25");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimePointStyleFalse1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "01:65");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimePointStyleFalse2() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "30:65");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}
	
	@Test
	public void testTimePointStyleFalse3() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, ":25");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeStyleErrorId);
	}

	@Test
	public void testTimePointMinTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "01:01");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointMinTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "01:00");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointMinFalse() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "00:30");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}
	
	@Test
	public void testTimePointMinFalse1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 6030);
		Optional<String> result = TimePointValidator.validate(constraint, "00:29");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMinErrorId);
	}
	
	@Test
	public void testTimePointMaxTrue() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = TimePointValidator.validate(constraint, "10:29");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointMaxTrue1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = TimePointValidator.validate(constraint, "10:30");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTimePointMaxFalse() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = TimePointValidator.validate(constraint, "10:31");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}
	
	@Test
	public void testTimePointMaxFalse1() {
		TimePointConstraint constraint = new TimePointConstraint(1, 60, 630);
		Optional<String> result = TimePointValidator.validate(constraint, "10:35");
		Assert.assertEquals(result.get(), ErrorIdFactory.TimeMaxErrorId);
	}
	
}
