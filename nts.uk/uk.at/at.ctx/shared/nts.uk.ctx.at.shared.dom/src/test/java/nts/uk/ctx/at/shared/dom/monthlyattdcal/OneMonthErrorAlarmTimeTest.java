package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.lang3.tuple.Pair;


public class OneMonthErrorAlarmTimeTest {

	@Test
	public void getters() {
		OneMonthErrorAlarmTime oneMonthErrorAlarmTime = new OneMonthErrorAlarmTime();
		NtsAssert.invokeGetters(oneMonthErrorAlarmTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20),new AgreementOneMonthTime(50));
		});
	}

	@Test
	public void createTest_2() {
		OneMonthErrorAlarmTime target = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));

		Assert.assertEquals(new AgreementOneMonthTime(50),target.getError());
		Assert.assertEquals(new AgreementOneMonthTime(20),target.getAlarm());
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		OneMonthErrorAlarmTime target = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.isErrorTimeOver(new AgreementOneMonthTime(60));

		Assert.assertEquals(true,result.getLeft());
		Assert.assertEquals(new AgreementOneMonthTime(50),result.getRight());
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		OneMonthErrorAlarmTime target = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.isErrorTimeOver(new AgreementOneMonthTime(10));

		Assert.assertEquals(false,result.getLeft());
		Assert.assertEquals(new AgreementOneMonthTime(50),result.getRight());
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		OneMonthErrorAlarmTime target = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calcAlarmTime(new AgreementOneMonthTime(40));

		Assert.assertEquals(new AgreementOneMonthTime(10),result);
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		OneMonthErrorAlarmTime target = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calcAlarmTime(new AgreementOneMonthTime(30));

		Assert.assertEquals(new AgreementOneMonthTime(0),result);
	}

}
