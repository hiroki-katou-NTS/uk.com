package nts.uk.shr.com.validate.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.DataConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;
import nts.uk.shr.com.validate.constraint.implement.NumericConstraint;
import nts.uk.shr.com.validate.constraint.implement.StringCharType;
import nts.uk.shr.com.validate.constraint.implement.StringConstraint;
import nts.uk.shr.com.validate.constraint.implement.TimeConstraint;
import nts.uk.shr.com.validate.constraint.implement.TimePointConstraint;
import nts.uk.shr.com.validate.validator.ErrorIdFactory;

public class CellValidateServiceTest {

	@Test
	public void testString() {
		DataConstraint constraint = new StringConstraint(1, StringCharType.ALPHABET, 10);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("abc123"));
		Assert.assertEquals(result.get(), ErrorIdFactory.CharTypeErrorId);
	}

	@Test
	public void testNumeric() {
		DataConstraint constraint = new NumericConstraint(1, false, new BigDecimal("0"), new BigDecimal("10"), 2, 2);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("-5"));
		Assert.assertEquals(result.get(), ErrorIdFactory.MinusErrorId);
	}

	@Test
	public void testDate() {
		DataConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("2018/11/123"));
		Assert.assertEquals(result.get(), ErrorIdFactory.DateErrorId);
	}

	@Test
	public void testTime() {
		DataConstraint constraint = new TimeConstraint(1, 0, 60);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("20:62"));
		Assert.assertEquals(result.get(), "MsgB_55");
	}

	@Test
	public void testTimePoint() {
		DataConstraint constraint = new TimePointConstraint(1, 0, 60);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("30:00"));
		Assert.assertEquals(result.get(), "MsgB_56");
	}

	@Test
	public void testDayAttr1() {
		DataConstraint constraint = new TimePointConstraint(1, 0, 60);
		Optional<String> result = CellValidateService.validateValue(constraint, new ValueWithType("前日23:00"));
		Assert.assertEquals(result.get(), "MsgB_56");
	}

}
