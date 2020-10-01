package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneYearErrorAlarmTimeTest {

	@Test
	public void getters() {
        OneYearErrorAlarmTime errorTimeInYear = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		NtsAssert.invokeGetters(errorTimeInYear);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
            OneYearErrorAlarmTime.of(new AgreementOneYearTime(10),new AgreementOneYearTime(20));
		});
	}

	@Test
	public void createTest_2() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));;

		Assert.assertEquals( new AgreementOneYearTime(30),target.getError());
		Assert.assertEquals( new AgreementOneYearTime(20),target.getAlarm());
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(40));

		Assert.assertEquals( true,result.getLeft());
		Assert.assertEquals( new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(10));

		Assert.assertEquals( false,result.getLeft());
		Assert.assertEquals( new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void calculateAlarmTimeTest_1() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(40));

		Assert.assertEquals(new AgreementOneYearTime(30),result);
	}

	@Test
	public void calculateAlarmTimeTest_2() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(10));

		Assert.assertEquals(new AgreementOneYearTime(0),result);
	}
}
