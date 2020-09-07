package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult.hoursPerMonth.ErrorTimeInMonth;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;


public class ErrorTimeInMonthTest {

	@Test
	public void getters() {
		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(0),new AgreementOneMonthTime(1));
		NtsAssert.invokeGetters(errorTimeInMonth);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(1));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(1),new AgreementOneMonthTime(2));

		Assert.assertEquals(target.getErrorTime(), new AgreementOneMonthTime(1));
		Assert.assertEquals(target.getAlarmTime(), new AgreementOneMonthTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(1),new AgreementOneMonthTime(2));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(4));

		Assert.assertEquals(result,new AgreementOneMonthTime(5));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(1),new AgreementOneMonthTime(2));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(-2));

		Assert.assertEquals(result,new AgreementOneMonthTime(0));
	}

}
