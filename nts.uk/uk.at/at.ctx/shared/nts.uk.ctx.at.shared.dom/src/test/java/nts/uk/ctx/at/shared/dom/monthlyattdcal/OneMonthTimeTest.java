package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.OverState;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneMonthTimeTest {

	@Test
	public void getters() {
		OneMonthTime oneMonthTime = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(0),
						new AgreementOneMonthTime(1)),new AgreementOneMonthTime(2));
		NtsAssert.invokeGetters(oneMonthTime);
	}

	@Test
	public void createTest_1() {
		NtsAssert.businessException("Msg_59", ()->{
			OneMonthTime.create(new ErrorTimeInMonth(new AgreementOneMonthTime(0),
					new AgreementOneMonthTime(1)),new AgreementOneMonthTime(2));
		});
	}

	@Test
	public void createTest_2() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));

		Assert.assertEquals(target.getErrorTimeInMonth(), errorTimeInMonth);
		Assert.assertEquals(target.getUpperLimitTime(), new AgreementOneMonthTime(1));
	}

	@Test
	public void errorCheckTest_Normal() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		OverState result =  target.errorCheck(new AttendanceTimeMonth(3));

		Assert.assertEquals(result.value, OverState.NORMAL.value);
	}

	@Test
	public void errorCheckTest_AlarmOver() {
		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(3),new AgreementOneMonthTime(1));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		OverState result =  target.errorCheck(new AttendanceTimeMonth(3));

		Assert.assertEquals(result.value, OverState.ALARM_OVER.value);
	}

	@Test
	public void errorCheckTest_ErrorOver() {
		ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(3),new AgreementOneMonthTime(1));
		OneMonthTime target = new OneMonthTime(errorTimeInMonth,new AgreementOneMonthTime(4));
		OverState result =  target.errorCheck(new AttendanceTimeMonth(4));

		Assert.assertEquals(result.value, OverState.ERROR_OVER.value);
	}

	@Test
	public void errorCheckTest_MaxTime() {
        ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
        OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		OverState result =  target.errorCheck(new AttendanceTimeMonth(4));

		Assert.assertEquals(result.value, OverState.UPPER_LIMIT_OVER.value);
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(4));

		Assert.assertEquals(result.getLeft(), true);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		Pair<Boolean, AgreementOneMonthTime> result =  target.checkErrorTimeExceeded(new AgreementOneMonthTime(1));

		Assert.assertEquals(result.getLeft(), false);
		Assert.assertEquals(result.getRight(), new AgreementOneMonthTime(2));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(4));

		Assert.assertEquals(result,new AgreementOneMonthTime(5));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		ErrorTimeInMonth errorTimeInMonth = ErrorTimeInMonth.create(new AgreementOneMonthTime(2),new AgreementOneMonthTime(3));
		OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(1));
		AgreementOneMonthTime result =  target.calculateAlarmTime(new AgreementOneMonthTime(-4));

		Assert.assertEquals(result,new AgreementOneMonthTime(0));
	}

}
