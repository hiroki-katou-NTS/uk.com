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
	public void testStyleFalse00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "a");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testStyleFalseWithDot00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "1,5");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testStyleFalseWithDot01() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "1;5");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testStyleFalseWithSpace00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "1.5 ");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testStyleFalseWithSpace01() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, " 1.5");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testStyleFalseWithSpace02() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, " 1. 5");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}
	
	@Test
	public void testMinusFalse() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-1");
		Assert.assertEquals(result.get(), ErrorIdFactory.MinusErrorId);
	}
	
	@Test
	public void testMinMax00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-6");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMinErrorId);
	}
	
	@Test
	public void testMinMax01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-5");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinMax02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "-4");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinMax03() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "0");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinMax04() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "9");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinMax05() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testMinMax06() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "11");
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMaxErrorId);
	}
	
	@Test
	public void testIntegerPart00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "1.3");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testIntegerPart01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "11.3");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testIntegerPart02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "111.3");
		Assert.assertEquals(result.get(), ErrorIdFactory.IntegerPartErrorId);
	}
	
	@Test
	public void testDecimalPart00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10.3");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testDecimalPart01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10.33");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testDecimalPart02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2, 2);
		Optional<String> result = NumericValidator.validate(constraint, "10.333");
		Assert.assertEquals(result.get(), ErrorIdFactory.DecimalPartErrorId);
	}

}
