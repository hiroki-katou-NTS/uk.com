package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;


public class ErrorTimeInMonthTest {

	@Test
	public void getters() {
		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(50), new AgreementOneMonthTime(20));
		NtsAssert.invokeGetters(errorTimeInMonth);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			ErrorTimeInMonth.create(new AgreementOneMonthTime(20),new AgreementOneMonthTime(50));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));

		Assert.assertEquals(new AgreementOneMonthTime(50),target.getErrorTime());
		Assert.assertEquals(new AgreementOneMonthTime(20),target.getAlarmTime());
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(60));

		Assert.assertEquals(true,result.getLeft());
		Assert.assertEquals(new AgreementOneMonthTime(50),result.getRight());
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(10));

		Assert.assertEquals(false,result.getLeft());
		Assert.assertEquals(new AgreementOneMonthTime(50),result.getRight());
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(40));

		Assert.assertEquals(new AgreementOneMonthTime(10),result);
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInMonth target = ErrorTimeInMonth.create(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(30));

		Assert.assertEquals(new AgreementOneMonthTime(0),result);
	}

}
