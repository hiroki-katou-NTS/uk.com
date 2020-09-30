package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneYearTimeTest {


	@Test
	public void getters() {
		OneYearTime oneMonthTime = OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		NtsAssert.invokeGetters(oneMonthTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),
					new AgreementOneYearTime(20)),new AgreementOneYearTime(10));
		});
	}

	@Test
	public void createTest_2() {
        OneYearErrorAlarmTime errorTimeInYear = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20));

		OneYearTime target = OneYearTime.of(errorTimeInYear,new AgreementOneYearTime(40));

		Assert.assertEquals(errorTimeInYear.getError(),target.getErAlTime().getError());
		Assert.assertEquals(errorTimeInYear.getAlarm(),target.getErAlTime().getAlarm());
		Assert.assertEquals(new AgreementOneYearTime(40),target.getUpperLimit());
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		OneYearTime target = OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(50));

		Assert.assertEquals(true,result.getLeft());
		Assert.assertEquals(new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		OneYearTime target = OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(10));

		Assert.assertEquals(false,result.getLeft());
		Assert.assertEquals(new AgreementOneYearTime(30),result.getRight());
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		OneYearTime target = OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(50));

		Assert.assertEquals(new AgreementOneYearTime(40),result);
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		OneYearTime target = OneYearTime.of(OneYearErrorAlarmTime.of(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(10));

		Assert.assertEquals(new AgreementOneYearTime(0),result);
	}
}
