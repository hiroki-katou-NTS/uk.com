package nts.uk.ctx.bs.person.dom.person.personal.anniversary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryNoticeDto;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryNotice;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;


/**
 * Test Domain service 新記念日があるか
 */
@RunWith(JMockit.class)
public class IsTodayHaveNewAnniversaryTest {

	@Tested
	private AnniversaryDomainService domainService;

	@Injectable
	private AnniversaryRepository require;

	@Mocked
	private static AnniversaryNoticeDto mockDto = AnniversaryNoticeDto.builder().personalId("personalId").noticeDay(0)
			.seenDate(GeneralDate.ymd(1999,10,07)).anniversary("1231").anniversaryTitle("anniversaryTitle")
			.notificationMessage("notificationMessage").build();

	@Test
	public void testListNotEmpty() {
		AnniversaryNotice domain = AnniversaryNotice.createFromMemento(mockDto);
		List<AnniversaryNotice> list = new ArrayList<AnniversaryNotice>();
		list.add(domain);
		new Expectations() {
			{
				require.getTodayAnniversary(GeneralDate.today());
				result = list;
			}
		};
		boolean res = domainService.isTodayHaveNewAnniversary();
		assertThat(res).isTrue();
	}
	
	@Test
	public void testListEmpty() {
		List<AnniversaryNotice> list = new ArrayList<AnniversaryNotice>();
		new Expectations() {
			{
				require.getTodayAnniversary(GeneralDate.today());
				result = list;
			}
		};
		boolean res = domainService.isTodayHaveNewAnniversary();
		assertThat(res).isFalse();
	}

}