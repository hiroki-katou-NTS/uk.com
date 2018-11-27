package nts.uk.shr.com.validate.validator;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;

public class NumericValidatorTest {

	@Test
	public void testTrue() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("5.55"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testTrue1() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("05.55"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testStyleFalse00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("a"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testStyleFalseWithDot00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("1,5"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testStyleFalseWithDot01() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("1;5"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testStyleFalseWithSpace00() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("1.5 "));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testStyleFalseWithSpace01() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType(" 1.5"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testStyleFalseWithSpace02() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType(" 1. 5"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericType);
	}

	@Test
	public void testMinusFalse() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("-1"));
		Assert.assertEquals(result.get(), ErrorIdFactory.MinusErrorId);
	}

	@Test
	public void testMinMax00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("-6"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMinErrorId);
	}

	@Test
	public void testMinMax01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("-5"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("-4"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax03() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("0"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax04() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("9"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax05() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("10"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax06() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType("11"));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMaxErrorId);
	}

	@Test
	public void testIntegerPart00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("1.3"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testIntegerPart01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("11.3"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testIntegerPart02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("111.3"));
		Assert.assertEquals(result.get(), ErrorIdFactory.IntegerPartErrorId);
	}

	@Test
	public void testDecimalPart00() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("10.3"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testDecimalPart01() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("10.33"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testDecimalPart02() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType("10.333"));
		Assert.assertEquals(result.get(), ErrorIdFactory.DecimalPartErrorId);
	}
	
	
	@Test
	public void testMinusFalseFloat() {
		NumericConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)-1));
		Assert.assertEquals(result.get(), ErrorIdFactory.MinusErrorId);
	}

	@Test
	public void testMinMax00Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)-6));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMinErrorId);
	}

	@Test
	public void testMinMax01Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)-5));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax02Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)-4));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax03Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)0));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax04Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)9));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax05Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)10));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testMinMax06Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("10"), 2, 2);
		Optional<String> result = constraint.validate(new ValueWithType((float)11));
		Assert.assertEquals(result.get(), ErrorIdFactory.NumericMaxErrorId);
	}

	@Test
	public void testIntegerPart00Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)1.3));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testIntegerPart01Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)11.3));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testIntegerPart02Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("0"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)111.3));
		Assert.assertEquals(result.get(), ErrorIdFactory.IntegerPartErrorId);
	}

	@Test
	public void testDecimalPart00Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)10.3));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testDecimalPart01Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)10.33));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testDecimalPart02Float() {
		NumericConstraint constraint = new NumericConstraint(1, true, new BigDecimal("-5"), new BigDecimal("1000"), 2,
				2);
		Optional<String> result = constraint.validate(new ValueWithType((float)10.333));
		Assert.assertEquals(result.get(), ErrorIdFactory.DecimalPartErrorId);
	}

}
