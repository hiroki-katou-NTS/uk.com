package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneYearTimeTest {

	private OneYearTime create(){
		return new OneYearTime(
				new ErrorTimeInYear(new AgreementOneYearTime(1),new AgreementOneYearTime(0)),
				new AgreementOneYearTime(2));
	}

	@Test
	public void getters() {
		OneYearTime oneMonthTime = create();
		NtsAssert.invokeGetters(oneMonthTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(1),
					new AgreementOneYearTime(0)),new AgreementOneYearTime(1));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInYear errorTimeInYear = new ErrorTimeInYear(new AgreementOneYearTime(1),new AgreementOneYearTime(0));
		OneYearTime target = create();

		Assert.assertEquals(target.getErrorTimeInYear().getErrorTime(), errorTimeInYear.getErrorTime());
		Assert.assertEquals(target.getErrorTimeInYear().getAlarmTime(), errorTimeInYear.getAlarmTime());
		Assert.assertEquals(target.getUpperLimitTime(), new AgreementOneYearTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		OneYearTime target = create();
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(1));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		OneYearTime target = create();
		Pair<Boolean, AgreementOneYearTime> result =  target.checkErrorTimeExceeded(new AgreementOneYearTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneYearTime(1));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		OneYearTime target = create();
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(4));

		Assert.assertEquals(result,new AgreementOneYearTime(3));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		OneYearTime target = create();
		AgreementOneYearTime result =  target.calculateAlarmTime(new AgreementOneYearTime(1));

		Assert.assertEquals(result,new AgreementOneYearTime(0));
	}
}
