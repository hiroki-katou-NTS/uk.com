package nts.uk.ctx.sys.portal.dom.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class ToppageAlarmLogTest {

	final ToppageAlarmLog domain = ToppageAlarmLog.builder()
			.cid("000000000000-0001")
			.alarmClassification(AlarmClassification.ALARM_LIST)
			.identificationKey(new IdentificationKey("01"))
			.displaySId("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570")
			.displayAtr(DisplayAtr.PIC)
			.alreadyDatetime(GeneralDateTime.now())
			.build();
	
	@Test
	public void getters() {
		// then
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void testBuilder() {
		String domainBuilder = ToppageAlarmLog.builder().toString();
		assertThat(domainBuilder.isEmpty()).isFalse();
	}
	
	@Test
	public void testUpdateAlreadyDatetime() {
		GeneralDateTime fakeTime = GeneralDateTime.now();
		domain.updateAlreadyDatetime();
		assertThat(domain.getAlreadyDatetime()).isEqualTo(fakeTime);
	}
	
	@Test
	public void testChangeToUnread() {
		GeneralDateTime currentTime = GeneralDateTime.now();
		domain.changeToUnread(currentTime);
		assertThat(domain.getAlreadyDatetime()).isEqualTo(currentTime.addMinutes(-1));
	}
	
}
