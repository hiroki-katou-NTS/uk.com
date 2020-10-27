package nts.uk.ctx.sys.auth.dom.anniversary.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;
import javax.enterprise.inject.spi.CDI;
/**
 * Test DomainService 新記念日があるか
 */
@RunWith(JMockit.class)
public class isTodayHaveNewAnniversaryTest {
	
	@Tested
	AnniversaryDomainService domainService;

	@Injectable
	private AnniversaryRepository require;

	@Test
	public void test() {
		//CDI.current();
		String loginPersonalId = "b1a068b2-d80e-4283-ae6c-29e9b1d1c02d";
		GeneralDate checkdate = GeneralDate.ymd(2020, 10, 7);

		new Expectations() {
			{
				require.getTodayAnniversary(checkdate, loginPersonalId);
				result = new ArrayList<AnniversaryNotice>();
			}
		};
		
        boolean res = domainService.isTodayHaveNewAnniversary();
		assertThat(res).isFalse();
	}
}
