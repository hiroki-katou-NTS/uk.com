package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreement.management.oneYear;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.apache.commons.lang3.tuple.Pair;
import static org.assertj.core.api.Assertions.assertThat;
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

		assertThat(target.getError()).isEqualTo(new AgreementOneYearTime(30));
		assertThat(target.getAlarm()).isEqualTo(new AgreementOneYearTime(20));
	}

	@Test
	public void checkErrorTimeExceededTest_1() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(40));

		assertThat(result.getLeft()).isEqualTo(true);
		assertThat(result.getRight()).isEqualTo(new AgreementOneYearTime(30));
	}

	@Test
	public void checkErrorTimeExceededTest_2() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		Pair<Boolean, AgreementOneYearTime> result =  target.isErrorTimeOver(new AgreementOneYearTime(10));

		assertThat(result.getLeft()).isEqualTo(false);
		assertThat(result.getRight()).isEqualTo( new AgreementOneYearTime(30));
	}

	@Test
	public void calculateAlarmTimeTest_1() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(40));

		assertThat(result).isEqualTo(new AgreementOneYearTime(30));
	}

	@Test
	public void calculateAlarmTimeTest_2() {
        OneYearErrorAlarmTime target = OneYearErrorAlarmTime.of(new AgreementOneYearTime(30), new AgreementOneYearTime(20));
		AgreementOneYearTime result =  target.calcAlarmTime(new AgreementOneYearTime(10));

		assertThat(result).isEqualTo(new AgreementOneYearTime(0));
	}
}
