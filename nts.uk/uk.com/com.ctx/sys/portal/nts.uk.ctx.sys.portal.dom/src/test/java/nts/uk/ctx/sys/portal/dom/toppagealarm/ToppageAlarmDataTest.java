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
	public void testUpdateTrueResolved() {
		domain.updateIsResolved(true);
		assertThat(domain.getIsResolved()).isTrue();
	}

	@Test
	public void testUpdateFalseResolved() {
		domain.updateIsResolved(false);
		assertThat(domain.getIsResolved()).isFalse();
	}
	
	@Test
	public void testUpdateOccurrenceDateTime() {
		GeneralDateTime occurrenceDateTime = GeneralDateTime.fromString("2020-02-02 20:00:00.0", "yyyy-MM-dd HH:mm:ss.s");
		domain.setOccurrenceDateTime(occurrenceDateTime);
		assertThat(domain.getOccurrenceDateTime()).isEqualByComparingTo(occurrenceDateTime);
	}
	
	@Test
	public void testNoArgsConstructor() {
		ToppageAlarmData domain = new ToppageAlarmData();
		assertThat(domain.getAlarmClassification()).isNull();
		assertThat(domain.getCid()).isNull();
		assertThat(domain.getIdentificationKey()).isNull();
		assertThat(domain.getDisplaySId()).isNotNull();
		assertThat(domain.getDisplayAtr()).isNotNull();
		assertThat(domain.getIsResolved()).isNull();
		assertThat(domain.getOccurrenceDateTime()).isNull();
		assertThat(domain.getDisplayMessage()).isNull();
		assertThat(domain.getLinkUrl()).isNull();
	}
	
	@Test
	public void testNullValueWithDisplaySidAndAtr() {
		ToppageAlarmData domain = ToppageAlarmData.builder().build();
		assertThat(domain.getDisplaySId()).isNotNull();
		assertThat(domain.getDisplayAtr()).isNotNull();
	}
	
	@Test
	public void testAllArgsConstructor() {
		String cid = "000000000000-0001";
		AlarmClassification alarmClassification = AlarmClassification.ALARM_LIST;
		IdentificationKey identificationKey = new IdentificationKey("01");
		String displaySId = "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570";
		DisplayAtr displayAtr = DisplayAtr.PIC;
		Boolean isResolved = true;
		GeneralDateTime occurrenceDateTime = GeneralDateTime.now();
		DisplayMessage displayMessage = new DisplayMessage("test message");
		Optional<LinkURL> linkURL = Optional.of(new LinkURL("http://google.com.vn"));

		ToppageAlarmData domain = new ToppageAlarmData(cid, alarmClassification, identificationKey
				, displaySId, displayAtr, isResolved, occurrenceDateTime, displayMessage, linkURL);
		
		assertThat(domain.getCid()).isEqualTo(cid);
		assertThat(domain.getAlarmClassification()).isEqualTo(alarmClassification);
		assertThat(domain.getIdentificationKey()).isEqualTo(identificationKey);
		assertThat(domain.getDisplaySId()).isEqualTo(displaySId);
		assertThat(domain.getDisplayAtr()).isEqualTo(displayAtr);
		assertThat(domain.getIsResolved()).isEqualTo(isResolved);
		assertThat(domain.getOccurrenceDateTime()).isEqualTo(occurrenceDateTime);
		assertThat(domain.getDisplayMessage()).isEqualTo(displayMessage);
		assertThat(domain.getLinkUrl()).isEqualTo(linkURL);
	}

}
