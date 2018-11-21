package nts.uk.shr.com.validate.validator;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;

public class NumericValidatorTest {

	@Test
	public void testTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "5.55");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testTrue1() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "05.55");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testStyleFalse() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "a");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testMinusFalse() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-1");
		Assert.assertEquals(result.get(), ErrorIdFactory.MinusErrorId);
	}
	
	
	@Test
	public void testMinTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-5");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinFalse() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-6");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMinErrorId);
	}
	
	@Test
	public void testMaxTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMaxFalse() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "11");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMaxErrorId);
	}
	
	@Test
	public void testIntegerPart() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "100.3");
		Assert.assertEquals(result.get(), ErrorIdFactory.IntegerPartErrorId);
	}
	
	@Test
	public void testDecimalPart() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10.333");
		Assert.assertEquals(result.get(), ErrorIdFactory.DecimalPartErrorId);
	}

}
