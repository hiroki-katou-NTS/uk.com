package nts.uk.ctx.sys.portal.dom.toppagealarm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

public class ToppageAlarmDataTest {

	@Test
	public void getters() {
		// then
		NtsAssert.invokeGetters(ToppageAlarmDataHelper.mockUnResolveDomain());
	}

	@Test
	public void testBuilder() {
		String domainBuilder = ToppageAlarmData.builder().toString();
		assertThat(domainBuilder.isEmpty()).isFalse();
	}
	
	@Test
	public void changeResolvedStatusTest() {
		//give
		ToppageAlarmData domain = ToppageAlarmDataHelper.mockUnResolveDomain();
		
		//when
		domain.changeResolvedStatus();
		
		//then
		assertThat(domain.getIsResolved()).isTrue();
	}
	
	@Test
	public void updateOccurrenceDateTimeTest() {
		//give
		ToppageAlarmData domain = ToppageAlarmDataHelper.mockResolvedDomain();
		
		//when
		domain.updateOccurrenceDateTime(GeneralDateTime.FAKED_NOW);
		
		//then
		assertThat(domain.getIsResolved()).isFalse();
		assertThat(domain.getOccurrenceDateTime()).isEqualTo(GeneralDateTime.FAKED_NOW);
	}

	@Test
	public void testAllArgsConstructor() {
		
		//give
		String cid = "000000000000-0001";
		AlarmClassification alarmClassification = AlarmClassification.ALARM_LIST;
		String displaySId = "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570";
		DisplayAtr displayAtr = DisplayAtr.PIC;
		Boolean isResolved = true;
		GeneralDateTime occurrenceDateTime = GeneralDateTime.now();
		DisplayMessage displayMessage = new DisplayMessage("test message");
		Optional<LinkURL> linkURL = Optional.of(new LinkURL("http://google.com.vn"));
		Optional<GeneralDateTime> readDateTime = Optional.of(GeneralDateTime.now());
		Optional<AlarmListPatternCode> patternCode = Optional.of(new AlarmListPatternCode("patternCode"));
		Optional<NotificationId> notificationId = Optional.of(new NotificationId("notificationId"));
		
		//then
		ToppageAlarmData domain = new ToppageAlarmData(cid, alarmClassification, displaySId, displayAtr, isResolved,
				occurrenceDateTime, displayMessage, linkURL, readDateTime, patternCode, notificationId);

		//when
		assertThat(domain.getCid()).isEqualTo(cid);
		assertThat(domain.getAlarmClassification()).isEqualTo(alarmClassification);
		assertThat(domain.getDisplaySId()).isEqualTo(displaySId);
		assertThat(domain.getDisplayAtr()).isEqualTo(displayAtr);
		assertThat(domain.getIsResolved()).isEqualTo(isResolved);
		assertThat(domain.getOccurrenceDateTime()).isEqualTo(occurrenceDateTime);
		assertThat(domain.getDisplayMessage()).isEqualTo(displayMessage);
		assertThat(domain.getLinkUrl()).isEqualTo(linkURL);
		assertThat(domain.getReadDateTime()).isEqualTo(readDateTime);
		assertThat(domain.getPatternCode()).isEqualTo(patternCode);
		assertThat(domain.getNotificationId()).isEqualTo(notificationId);
	}

}
