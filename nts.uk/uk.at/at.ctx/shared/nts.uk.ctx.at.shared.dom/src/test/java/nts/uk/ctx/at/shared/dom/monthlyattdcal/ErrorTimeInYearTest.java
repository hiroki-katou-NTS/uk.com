package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class ErrorTimeInYearTest {

	@Test
	public void getters() {
		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneYearTime(0),new AgreementOneYearTime(1));
		NtsAssert.invokeGetters(errorTimeInYear);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			ErrorTimeInYear.create(new AgreementOneYearTime(2),new AgreementOneYearTime(1));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(1),new AgreementOneYearTime(2));

		Assert.assertEquals(target.getErrorTime(), new AgreementOneYearTime(1));
		Assert.assertEquals(target.getAlarmTime(), new AgreementOneYearTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(2),new AgreementOneYearTime(3));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(2),new AgreementOneYearTime(3));
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(2));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(1),new AgreementOneYearTime(2));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(4));

		Assert.assertEquals(result,new AgreementOneYearTime(5));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInYear target = ErrorTimeInYear.create(new AgreementOneYearTime(1),new AgreementOneYearTime(2));
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(-2));

		Assert.assertEquals(result,new AgreementOneYearTime(0));
	}
}
