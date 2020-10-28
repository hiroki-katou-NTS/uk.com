package nts.uk.ctx.sys.auth.dom.anniversary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.MonthDay;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;

/**
 * Test DomainService 新記念日があるか
 */
@RunWith(JMockit.class)
public class isTodayHaveNewAnniversaryTest {

	@Tested
	private AnniversaryDomainService domainService;

	@Injectable
	private AnniversaryRepository require;

	@Test
	public void test() {
		GeneralDate checkdate = GeneralDate.today();
		AnniversaryNotice mock = new AnniversaryNotice(
				"personalId", 1,
				MonthDay.parse("0710", AnniversaryNotice.FORMAT_MONTH_DAY), 
				"anniversaryTitle", "notificationMessage");

		new Expectations() {
			{
				require.getTodayAnniversary(checkdate);
				result = new ArrayList<AnniversaryNotice>().add(mock);
			}
		};

		boolean res = domainService.isTodayHaveNewAnniversary();
		assertThat(res).isFalse();
	}
}
