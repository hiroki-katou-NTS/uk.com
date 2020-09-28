package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneYearTimeTest {


	@Test
	public void getters() {
		OneYearTime oneMonthTime = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		NtsAssert.invokeGetters(oneMonthTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			OneYearTime.create(new ErrorTimeInYear(new AgreementOneYearTime(30),
					new AgreementOneYearTime(20)),new AgreementOneYearTime(10));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20));

		OneYearTime target = OneYearTime.create(errorTimeInYear,new AgreementOneYearTime(40));

		Assert.assertEquals(target.getErrorTimeInYear().getErrorTime(), errorTimeInYear.getErrorTime());
		Assert.assertEquals(target.getErrorTimeInYear().getAlarmTime(), errorTimeInYear.getAlarmTime());
		Assert.assertEquals(target.getUpperLimitTime(), new AgreementOneYearTime(40));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		OneYearTime target = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(50));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(30));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		OneYearTime target = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(10));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(30));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		OneYearTime target = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(50));

		Assert.assertEquals(result,new AgreementOneYearTime(40));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		OneYearTime target = new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(30),new AgreementOneYearTime(20)),
				new AgreementOneYearTime(40));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(10));

		Assert.assertEquals(result,new AgreementOneYearTime(0));
	}

	@Test
	public void checkError() {
		NtsAssert.businessException("Msg_59", ()->{
			OneYearTime oneYearTime = new OneYearTime(
					new ErrorTimeInYear(new AgreementOneYearTime(30), new AgreementOneYearTime(20)),
					new AgreementOneYearTime(10));
			oneYearTime.checkError(new AttendanceTimeYear(10));
		});
	}
}
