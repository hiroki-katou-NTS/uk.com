package nts.uk.ctx.sys.portal.dom.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class ToppageAlarmDataTest {
	
	final ToppageAlarmData domain = ToppageAlarmData.builder()
			.cid("000000000000-0001")
			.alarmClassification(AlarmClassification.ALARM_LIST)
			.identificationKey(new IdentificationKey("01"))
			.displaySId("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570")
			.displayAtr(DisplayAtr.PIC)
			.isResolved(false)
			.occurrenceDateTime(GeneralDateTime.now())
			.displayMessage(new DisplayMessage("test message"))
			.linkUrl(Optional.of(new LinkURL("http://google.com.vn")))
			.build();
	
	@Test
	public void getters() {
		// then
		NtsAssert.invokeGetters(domain);
	}
	
	@Test
	public void testBuilder() {
		String domainBuilder = ToppageAlarmData.builder().toString();
		assertThat(domainBuilder.isEmpty()).isFalse();
	}
	
	@Test
	public void testUpdateIsResolved() {
		boolean sample = true;
		domain.updateIsResolved(sample);
		assertThat(domain.getIsResolved()).isEqualTo(sample);
	}
	
}
