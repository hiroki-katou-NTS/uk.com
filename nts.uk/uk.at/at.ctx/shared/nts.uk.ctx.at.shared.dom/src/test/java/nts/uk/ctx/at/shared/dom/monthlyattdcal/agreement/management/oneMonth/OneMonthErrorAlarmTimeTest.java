package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreement.management.oneMonth;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import static org.assertj.core.api.Assertions.assertThat;
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
			OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20),new AgreementOneMonthTime(50));
		});
	}

	@Test
	public void createTest_2() {
		OneMonthErrorAlarmTime target = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));

		assertThat(target.getError()).isEqualTo(new AgreementOneMonthTime(50));
		assertThat(target.getAlarm()).isEqualTo(new AgreementOneMonthTime(20));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
		OneMonthErrorAlarmTime target = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.isErrorTimeOver(new AgreementOneMonthTime(60));

		assertThat(result.getLeft()).isEqualTo(true);
		assertThat(result.getRight()).isEqualTo(new AgreementOneMonthTime(50));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
		OneMonthErrorAlarmTime target = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		Pair<Boolean, AgreementOneMonthTime> result =  target.isErrorTimeOver(new AgreementOneMonthTime(10));

		assertThat(result.getLeft()).isEqualTo(false);
		assertThat(result.getRight()).isEqualTo(new AgreementOneMonthTime(50));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
		OneMonthErrorAlarmTime target = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calcAlarmTime(new AgreementOneMonthTime(40));

		assertThat(result).isEqualTo(new AgreementOneMonthTime(10));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
		OneMonthErrorAlarmTime target = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50),new AgreementOneMonthTime(20));
		AgreementOneMonthTime result =  target.calcAlarmTime(new AgreementOneMonthTime(30));

		assertThat(result).isEqualTo(new AgreementOneMonthTime(0));
	}

}
