package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import mockit.internal.startup.Startup;
import nts.arc.time.GeneralDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class ApproverGetDomainServiceTest {

	@Injectable
	private ApproverGetDomainService.Require require;

	@Tested
	private ByWorkplaceApproverGetDomainService byWkpApprGetDS;

//	@Before
//	public void before() {
//		byWkpApprGetDS = new ByWorkplaceApproverGetDomainService();
//	}

//	@BeforeClass
//	public static void jMockit(){
//		Startup.verifyInitialization();
//	}

	@Test
	public void test01(@Injectable("empId") String empId) {
		val service = new ApproverGetDomainService();
		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));

		new Expectations() {{
			byWkpApprGetDS.getApprover(require, empId);
			result = Optional.of(approverItem);
		}};

		assertThat(service.getApprover(require, empId).get()).isEqualTo(approverItem);
	}

//	@Test
//	public void test02() {
//		val empId = "empId";
//		val baseDate = GeneralDate.today();
//		val approverItem = new ApproverItem(Helper.createApproverList(5), Helper.createConfirmerList(5));
//		new Expectations() {{
//			byWkpApprGetDS.getApprover(require, empId);
//			result = Optional.empty();
//
//			require.getApproverHistoryItem(baseDate);
//			result = Optional.of(approverItem);
//		}};
//
//		val service = new ApproverGetDomainService();
//		assertThat(service.getApprover(require, empId).get()).isEqualTo(approverItem);
//	}
//
//	@Test
//	public void test03() {
//		val empId = "empId";
//		val baseDate = GeneralDate.today();
//		new Expectations() {{
//			byWkpApprGetDS.getApprover(require, empId);
//			result = Optional.empty();
//
//			require.getApproverHistoryItem(baseDate);
//			result = Optional.empty();
//		}};
//
//		val service = new ApproverGetDomainService();
//		assertThat(service.getApprover(require, empId)).isEqualTo(Optional.empty());
//	}
}
