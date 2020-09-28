package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class ErrorTimeInYearTest {

	@Test
	public void getters() {
		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		NtsAssert.invokeGetters(errorTimeInYear);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			ErrorTimeInYear.create(new AgreementOneYearTime(10),new AgreementOneYearTime(20));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(30), new AgreementOneYearTime(20));;

		Assert.assertEquals( new AgreementOneYearTime(30),target.getErrorTime());
		Assert.assertEquals( new AgreementOneYearTime(20),target.getAlarmTime());
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInYear target = new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(40));

		Assert.assertEquals( true,result.getLeft());
		Assert.assertEquals( new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInYear target = new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(10));

		Assert.assertEquals( false,result.getLeft());
		Assert.assertEquals( new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInYear target = new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(40));

		Assert.assertEquals(new AgreementOneYearTime(30),result);
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInYear target = new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(10));

		Assert.assertEquals(new AgreementOneYearTime(0),result);
	}
}
