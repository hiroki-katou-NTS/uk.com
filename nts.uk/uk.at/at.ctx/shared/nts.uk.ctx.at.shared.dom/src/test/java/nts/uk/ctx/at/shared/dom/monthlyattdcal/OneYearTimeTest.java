package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerYear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerYear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneYearTimeTest {

	@Test
	public void getters() {
		OneYearTime oneMonthTime = new OneYearTime(
				new ErrorTimeInYear(new AgreementOneMonthTime(0),
						new AgreementOneMonthTime(1)),
				new AgreementOneMonthTime(2));
		NtsAssert.invokeGetters(oneMonthTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			OneYearTime.create(new ErrorTimeInYear(new AgreementOneMonthTime(0),
					new AgreementOneMonthTime(1)),new AgreementOneMonthTime(2));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInYear errorTimeInYear = ErrorTimeInYear.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneMonthTime(1));

		Assert.assertEquals(target.getErrorTimeInYear(), errorTimeInYear);
		Assert.assertEquals(target.getUpperLimitTime(), new AgreementOneMonthTime(1));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInYear errorTimeInYear = ErrorTimeInYear.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneMonthTime(1));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInYear errorTimeInYear = ErrorTimeInYear.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneMonthTime(1));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInYear errorTimeInYear = ErrorTimeInYear.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneMonthTime(1));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(4));

		Assert.assertEquals(result,new AgreementOneMonthTime(5));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInYear errorTimeInYear = ErrorTimeInYear.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneMonthTime(1));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(-4));

		Assert.assertEquals(result,new AgreementOneMonthTime(0));
	}
}
