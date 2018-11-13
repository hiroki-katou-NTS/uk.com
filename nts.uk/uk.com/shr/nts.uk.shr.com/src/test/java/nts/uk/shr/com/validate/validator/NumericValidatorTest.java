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
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("5.55"));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinusFalse() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("-1"));
		Assert.assertEquals(result.get(), ErrorIdFactory.getMinusErrorId());
	}
	
	
	@Test
	public void testMinTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("-5"));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinFalse() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("-6"));
		Assert.assertEquals(result.get(), ErrorIdFactory.getNumericMinErrorId());
	}
	
	@Test
	public void testMaxTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("10"));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMaxFalse() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("11"));
		Assert.assertEquals(result.get(), ErrorIdFactory.getNumericMaxErrorId());
	}
	
	@Test
	public void testIntegerPart() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("100.3"));
		Assert.assertEquals(result.get(), ErrorIdFactory.getIntegerPartErrorId());
	}
	
	@Test
	public void testDecimalPart() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, new BigDecimal("10.333"));
		Assert.assertEquals(result.get(), ErrorIdFactory.getDecimalPartErrorId());
	}

}
