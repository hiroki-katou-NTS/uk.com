package nts.uk.ctx.sys.auth.dom.anniversary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNoticeDto;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;

/**
 * Test Domain service 期間で記念日情報を取得する
 */
@RunWith(JMockit.class)
public class SetFlagTest {

	@Tested
	private AnniversaryDomainService domainService;

	@Injectable
	private AnniversaryRepository require;

	@Mocked
	private static AnniversaryNoticeDto mockDto = AnniversaryNoticeDto.builder()
			.personalId("personalId")
			.noticeDay(1)
			.seenDate(GeneralDate.today()).anniversary("1007").anniversaryTitle("anniversaryTitle")
			.notificationMessage("notificationMessage").build();

	@Test
	public void testFirstIf() {
		DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require.getTodayAnniversary(GeneralDate.today());
				result = new ArrayList<AnniversaryNotice>();
			}
		};
		Map<AnniversaryNotice, Boolean> map = domainService.setFlag(datePeriod);
		assertThat(map).isEqualTo(new HashMap<AnniversaryNotice, Boolean>());
	}

	@Test
	public void testIfOfSecondIf() {
		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(1999, 10, 7), GeneralDate.ymd(1999, 10, 7));
		List<AnniversaryNotice> list = new ArrayList<>();
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		list.add(domain);
		new Expectations() {
			{
				require.getByDatePeriod(datePeriod);
				result = list;
			}
		};
		Map<AnniversaryNotice, Boolean> map = domainService.setFlag(datePeriod);
		assertThat(map.containsKey(domain)).isTrue();
	}

	@Test
	public void testElseOfSecondIf() {
		DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.ymd(1999, 10, 7));
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		Map<AnniversaryNotice, Boolean> map = domainService.setFlag(datePeriod);
		assertThat(map.containsKey(domain)).isFalse();
	}

}
