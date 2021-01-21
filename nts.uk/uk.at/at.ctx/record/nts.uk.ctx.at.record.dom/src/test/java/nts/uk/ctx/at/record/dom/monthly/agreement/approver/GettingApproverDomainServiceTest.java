package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class GettingApproverDomainServiceTest {

	@Injectable
	private GettingApproverDomainService.Require require;

	/**
	 * R2 return normal result
	 * 利用設定.職場を利用する = する
	 * 職場別の承認者を取得する#取得する returns normal result
	 */
	@Test
	public void test01() {
		String empId = "dummyEmp";
		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));

		new Expectations(){{
			require.getUsageSetting();
			result = new UnitOfApprover("dummyCid", DoWork.USE);
		}};

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(
					GetWorkplaceApproveHistoryDomainService.Require require, String empId) {

				return Optional.of(approverItem);
			}
		};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result.get()).isEqualTo(approverItem);
	}

	/**
	 * R2 return normal result
	 * 利用設定.職場を利用する = する
	 * 職場別の承認者を取得する#取得する returns empty result
	 * R1 return normal result
	 */
	@Test
	public void test02() {
		String empId = "dummyEmp";
		val approverHist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), GeneralDate.max()),
				Arrays.asList("approver01"),
				Arrays.asList("confirmer01")
		);

		new Expectations(){{
			require.getUsageSetting();
			result = new UnitOfApprover("dummyCid", DoWork.USE);
		}};

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(
					GetWorkplaceApproveHistoryDomainService.Require require,
					String empId) {

				return Optional.empty();
			}
		};

		val baseDate = GeneralDate.today();
		new Expectations(){{
			require.getApproverHistoryItem(baseDate);
			result = Optional.of(approverHist);
		}};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result.get().getApproverList()).isEqualTo(approverHist.getApproverList());
		assertThat(result.get().getConfirmerList()).isEqualTo(approverHist.getConfirmerList());
	}

	/**
	 * R2 return normal result
	 * 利用設定.職場を利用する = する
	 * 職場別の承認者を取得する#取得する returns empty result
	 * R1 return empty result
	 */
	@Test
	public void test03() {
		String empId = "dummyEmp";

		new Expectations(){{
			require.getUsageSetting();
			result = new UnitOfApprover("dummyCid", DoWork.USE);
		}};

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(
					GetWorkplaceApproveHistoryDomainService.Require require,
					String empId) {

				return Optional.empty();
			}
		};

		val baseDate = GeneralDate.today();
		new Expectations(){{
			require.getApproverHistoryItem(baseDate);
			result = Optional.empty();
		}};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result).isEmpty();
	}

	/**
	 * R2 return normal result
	 * 利用設定.職場を利用する != する
	 * 職場別の承認者を取得する#取得する returns normal result
	 * R1 return normal result
	 */
	@Test
	public void test04() {
		String empId = "dummyEmp";
		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));
		val approverHist = Approver36AgrByCompany.create(
				"cid",
				new DatePeriod(Helper.createDate("2020/09/01"), GeneralDate.max()),
				Arrays.asList("approver02"),
				Arrays.asList("confirmer02")
		);

		new Expectations(){{
			require.getUsageSetting();
			result = new UnitOfApprover("dummyCid", DoWork.NOTUSE);
		}};

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(
					GetWorkplaceApproveHistoryDomainService.Require require,
					String empId) {

				return Optional.of(approverItem);
			}
		};

		val baseDate = GeneralDate.today();
		new Expectations(){{
			require.getApproverHistoryItem(baseDate);
			result = Optional.of(approverHist);
		}};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result.get().getApproverList()).isEqualTo(approverHist.getApproverList());
		assertThat(result.get().getConfirmerList()).isEqualTo(approverHist.getConfirmerList());
	}

	/**
	 * R2 return normal result
	 * 利用設定.職場を利用する != する
	 * 職場別の承認者を取得する#取得する returns normal result
	 * R1 return empty result
	 */
	@Test
	public void test05() {
		String empId = "dummyEmp";
		val approverItem = new ApproverItem(Arrays.asList("approver01"), Arrays.asList("confirmer01"));

		new Expectations(){{
			require.getUsageSetting();
			result = new UnitOfApprover("dummyCid", DoWork.NOTUSE);
		}};

		new MockUp<GetWorkplaceApproveHistoryDomainService>() {
			@Mock
			Optional<ApproverItem> getWorkplaceApproveHistory(
					GetWorkplaceApproveHistoryDomainService.Require require,
					String empId) {

				return Optional.of(approverItem);
			}
		};

		val baseDate = GeneralDate.today();
		new Expectations(){{
			require.getApproverHistoryItem(baseDate);
			result = Optional.empty();
		}};

		val result = GettingApproverDomainService.getApprover(require, empId);
		assertThat(result).isEmpty();
	}
}