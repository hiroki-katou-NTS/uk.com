package nts.uk.ctx.sys.auth.dom.anniversary;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.MonthDay;

import org.junit.Test;

import mockit.Mocked;
import nts.arc.time.GeneralDate;

public class AnniversaryNoticeTest {

	@Mocked
	public static AnniversaryNoticeDto mockDto = AnniversaryNoticeDto.builder()
			.personalId("personalId")
			.noticeDay(1)
			.seenDate(GeneralDate.ymd(2020, 02, 02))
			.anniversary("1007")
			.anniversaryTitle("anniversaryTitle")
			.notificationMessage("notificationMessage").build();

	@Test
	public void createFromMementoAndGetMemento() {
		// when
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);

		// then
		assertThat(domain.getPersonalId()).isEqualTo(mockDto.getPersonalId());
		assertThat(domain.getNoticeDay().value).isEqualTo(mockDto.getNoticeDay());
		assertThat(domain.getSeenDate()).isEqualTo(mockDto.getSeenDate());
		assertThat(domain.getAnniversary()).isEqualTo(mockDto.getAnniversary());
		assertThat(domain.getAnniversaryTitle().v()).isEqualTo(mockDto.getAnniversaryTitle());
		assertThat(domain.getNotificationMessage().v()).isEqualTo(mockDto.getNotificationMessage());
	}

	@Test
	public void setMemento() {
		//given
		AnniversaryNoticeDto nullDto = AnniversaryNoticeDto.builder().build();
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		
		//when
		domain.setMemento(nullDto);
		
		//then
		assertThat(domain.getPersonalId()).isEqualTo(mockDto.getPersonalId());
		assertThat(domain.getNoticeDay().value).isEqualTo(mockDto.getNoticeDay());
		assertThat(domain.getSeenDate()).isEqualTo(mockDto.getSeenDate());
		assertThat(domain.getAnniversary()).isEqualTo(mockDto.getAnniversary());
		assertThat(domain.getAnniversaryTitle().v()).isEqualTo(mockDto.getAnniversaryTitle());
		assertThat(domain.getNotificationMessage().v()).isEqualTo(mockDto.getNotificationMessage());
		}
	
	@Test
	public void createFromContrucstor() {
		// when
		AnniversaryNotice domainTodayBefore = new AnniversaryNotice("personalId",1,MonthDay.of(12, 31),"anniversaryTitle","notificationMessage");
		AnniversaryNotice domainTodayAfter = new AnniversaryNotice("personalId",1,MonthDay.of(1, 1),"anniversaryTitle","notificationMessage");
		GeneralDate todayAnniversaryBefore = GeneralDate.ymd(GeneralDate.today().year()-1, 12, 31);
		GeneralDate todayAnniversaryAfter = GeneralDate.ymd(GeneralDate.today().year(), 1, 1);
		// then
		assertThat(domainTodayBefore.getSeenDate()).isEqualTo(todayAnniversaryBefore);
		assertThat(domainTodayAfter.getSeenDate()).isEqualTo(todayAnniversaryAfter);
	}
	
	@Test
	public void isNewAnniversaryTest() {
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		
		//last year but before 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2019,01,01))).isEqualTo(false);
		
		//last year but after 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2019,03,03))).isEqualTo(false);

		//this year but before 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2020,01,01))).isEqualTo(false);
		
		//this year but after 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2020,03,03))).isEqualTo(false);
		
		//next year but before 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2021,01,01))).isEqualTo(false);
		
		//next year but after 02/02
		assertThat(domain.isNewAnniversary(GeneralDate.ymd(2021,03,03))).isEqualTo(true);
	}
	
	@Test
	public void updateSeenDateTest( ) {
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		
		//before 10/07
		domain.updateSeenDate(GeneralDate.ymd(2020,1,1));
		assertThat(domain.getSeenDate()).isEqualTo(GeneralDate.ymd(2019,10,7));
		
		//after 10/07 (noticeDay = 1 before 1 day)
		domain.updateSeenDate(GeneralDate.ymd(2020,10,8));
		assertThat(domain.getSeenDate()).isEqualTo(GeneralDate.ymd(2020,10,7));
				
		//after 10/07
		domain.updateSeenDate(GeneralDate.ymd(2021,3,3));
		assertThat(domain.getSeenDate()).isEqualTo(GeneralDate.ymd(2020,10,7));
		
	}
}
