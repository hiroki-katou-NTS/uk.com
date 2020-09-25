package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;


public class ErrorTimeInMonthTest {

	private ErrorTimeInMonth create(){
		return new ErrorTimeInMonth(new AgreementOneMonthTime(3), new AgreementOneMonthTime(2));
	}

	@Test
	public void getters() {
		ErrorTimeInMonth errorTimeInMonth = create();
		NtsAssert.invokeGetters(errorTimeInMonth);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			new ErrorTimeInMonth(new AgreementOneMonthTime(1),new AgreementOneMonthTime(2));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInMonth target = create();

		Assert.assertEquals(target.getErrorTime(), new AgreementOneMonthTime(3));
		Assert.assertEquals(target.getAlarmTime(), new AgreementOneMonthTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInMonth target = create();
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(3));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInMonth target = create();
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(3));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInMonth target = create();
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(4));

		Assert.assertEquals(result,new AgreementOneMonthTime(3));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInMonth target = create();
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(1));

		Assert.assertEquals(result,new AgreementOneMonthTime(0));
	}

}
