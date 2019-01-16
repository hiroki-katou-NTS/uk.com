package nts.uk.shr.com.validate.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.gul.util.value.ValueWithType;
import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;

public class DateValidatorTest {
	// Year/Month/Date
	@Test
	public void testDateTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000/01/01"));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testDateTrue1() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType(LocalDate.of(2000, 1, 1)));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testDateTrue2() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType( new BigDecimal("2000")));
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testDateFalseWithType() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType(true));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	// --------------------- False with order.
	// ---------------------------------------
	@Test
	public void testDateFalseWithOrder00() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000/21/11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithOrder11() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11/21/2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithOrder12() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11/2018/21"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithOrder21() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21/11/2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithOrder22() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21/2018/11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	// ----------------False with connected characters
	// ---------------------------------------
	@Test
	public void testDateFalseWithConnectedCharacters00() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000-21-11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithConnectedCharacters01() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000-11-21"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithConnectedCharacters10() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11-21-2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithConnectedCharacters11() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11-2018-21"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithConnectedCharacters20() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21-11-2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithConnectedCharacters21() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21-2018-11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}
	// --------------False with not connected
	// characters--------------------------------------------

	@Test
	public void testDateFalseWithNotConnectedCharacters00() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("20181121"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithNotConnectedCharacters01() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("20182111"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithNotConnectedCharacters10() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11212018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithNotConnectedCharacters11() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("11201821"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithNotConnectedCharacters20() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21112018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalseWithNotConnectedCharacters21() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("21201811"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	// ------------------------------ Miss DateType
	// --------------------------------------

	@Test
	public void testDateFalse0() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("a/b/c"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalse1() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalse2() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2000/10"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalse3() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("1"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testDateFalse4() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = constraint.validate(new ValueWithType("2018/06/31"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	// Year/Month

	@Test
	public void testYearMonthTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("2018/11"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testYearMonthFalse00() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("201811"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse01() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("2018-11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse02() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("11/2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse03() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("11-2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse04() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("112018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse10() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("2018"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse11() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("2018/"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse20() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse21() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("/11"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearMonthFalse30() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = constraint.validate(new ValueWithType("aaa"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	// Year
	@Test
	public void testYearTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType("2018"));
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testYearFalse() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType("20181"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearFalse2() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType("201"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearFalse3() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType("-201"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

	@Test
	public void testYearFalse4() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = constraint.validate(new ValueWithType("a"));
		Assert.assertEquals(ErrorIdFactory.DateErrorId, result.get());
	}

}
