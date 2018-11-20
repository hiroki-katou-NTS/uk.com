package nts.uk.shr.com.validate.validator;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.shr.com.validate.constraint.implement.DateConstraint;
import nts.uk.shr.com.validate.constraint.implement.DateType;

public class DateValidatorTest {
	// Year/Month/Date
	@Test
	public void testDateTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, "2000/01/01");
		Assert.assertEquals(result.isPresent(), false);
	}

	@Test
	public void testDateFalse1() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, "2000");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testDateFalse2() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, "2000/10");
			Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testDateFalse3() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, 1);
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testDateFalse4() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, "a");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testDateFalse5() {
		DateConstraint constraint = new DateConstraint(1, DateType.DATE);
		Optional<String> result = DateValidator.validate(constraint, new Date());
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	// Year/Month
	
	@Test
	public void testYearMonthTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = DateValidator.validate(constraint, "2018/11");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testYearMonthFalse() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = DateValidator.validate(constraint, "201811");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testYearMonthFalse1() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = DateValidator.validate(constraint, "2018");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testYearMonthFalse2() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = DateValidator.validate(constraint, "11");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testYearMonthFalse3() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEARMONTH);
		Optional<String> result = DateValidator.validate(constraint, "2018-11");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	
	// Year
	@Test
	public void testYearTrue() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = DateValidator.validate(constraint, "2018");
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testYearTrue1() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = DateValidator.validate(constraint, 2018);
		Assert.assertEquals(result.isPresent(), false);
	}
	
	@Test
	public void testYearFalse() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = DateValidator.validate(constraint, "20181");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testYearFalse1() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = DateValidator.validate(constraint, 20181);
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
	@Test
	public void testYearFalse2() {
		DateConstraint constraint = new DateConstraint(1, DateType.YEAR);
		Optional<String> result = DateValidator.validate(constraint, "-20181");
		Assert.assertEquals(ErrorIdFactory.getDateErrorId(), result.get());
	}
	
}
