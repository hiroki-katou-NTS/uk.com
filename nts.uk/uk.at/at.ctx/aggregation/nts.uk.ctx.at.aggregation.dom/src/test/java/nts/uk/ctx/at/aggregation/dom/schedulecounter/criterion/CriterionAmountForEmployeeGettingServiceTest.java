package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;

/**
 * Test for GuidelineAmountForEmployeeGettingService
 * @author kumiko_otake
 */
public class CriterionAmountForEmployeeGettingServiceTest {

	@Injectable CriterionAmountForEmployeeGettingService.Require require;


	/**
	 * Target	: get
	 * Pattern	: 雇用別の目安金額 利用しない
	 * @param forCompany 会社の目安金額
	 */
	@Test
	public void test_get_forEmployeeIsNotUse(
			@Injectable CriterionAmountForCompany forCompany
		,	@Injectable CriterionAmountForEmployment forEmployment
	) {

		// 目安利用区分 雇用別＝使用しない
		val usageStg = CriterionAmountHelper.createUsageSetting("CmpId", false);

		new Expectations() {{
			// 目安利用区分
			require.getUsageSetting();
			result = Optional.of(usageStg);
			// 会社の目安金額
			require.getCriterionAmountForCompany();
			result = Optional.of(forCompany);
		}};

		// Execute
		val result = CriterionAmountForEmployeeGettingService
						.get( require, new EmployeeId("EmpId"), GeneralDate.today() );

		// Assertion
		assertThat( result ).isEqualTo( forCompany.getCriterionAmount() );
		assertThat( result ).isNotEqualTo( forEmployment.getCriterionAmount() );

	}

	/**
	 * Target	: get
	 * Pattern	: 雇用別の目安金額 利用する
	 * 				&& 社員の雇用期間 あり
	 * 				&& 雇用に対応する目安金額 なし
	 * @param forCompany 会社の目安金額
	 * @param forEmployment 雇用の目安金額
	 * @param empHist 社員の雇用履歴
	 */
	@Test
	public void test_get_forEmploymentIsNotExists(
			@Injectable CriterionAmountForCompany forCompany
		,	@Injectable CriterionAmountForEmployment forEmployment
		,	@Injectable EmploymentPeriodImported empHist
	) {

		// 目安利用区分 雇用別＝使用する
		val usageStg = CriterionAmountHelper.createUsageSetting("CmpId", true);

		new Expectations() {{
			// 目安利用区分
			require.getUsageSetting();
			result = Optional.of(usageStg);
			// 社員の雇用期間
			require.getEmploymentHistory( (EmployeeId)any, (GeneralDate)any );
			result = Optional.of(empHist);
			// 雇用の目安金額
			require.getCriterionAmountForEmployment( (EmploymentCode)any );
			// 会社の目安金額
			require.getCriterionAmountForCompany();
			result = Optional.of(forCompany);
		}};

		// Execute
		val result = CriterionAmountForEmployeeGettingService
						.get( require, new EmployeeId("EmpId"), GeneralDate.today() );

		// Assertion
		assertThat( result ).isEqualTo( forCompany.getCriterionAmount() );
		assertThat( result ).isNotEqualTo( forEmployment.getCriterionAmount() );

	}

	/**
	 * Target	: get
	 * Pattern	: 雇用別の目安金額 利用する
	 * 				&& 社員の雇用期間 あり
	 * 				&& 雇用に対応する目安金額 あり
	 * @param forCompany 会社の目安金額
	 * @param forEmployment 雇用の目安金額
	 * @param empHist 社員の雇用履歴
	 */
	@Test
	public void test_get_forEmploymentIsExists(
			@Injectable CriterionAmountForCompany forCompany
		,	@Injectable CriterionAmountForEmployment forEmployment
		,	@Injectable EmploymentPeriodImported empHist
	) {

		// 目安利用区分 雇用別＝使用する
		val usageStg = CriterionAmountHelper.createUsageSetting("CmpId", true);

		new Expectations() {{
			// 目安利用区分
			require.getUsageSetting();
			result = Optional.of(usageStg);
			// 社員の雇用期間
			require.getEmploymentHistory( (EmployeeId)any, (GeneralDate)any );
			result = Optional.of(empHist);
			// 雇用の目安金額
			require.getCriterionAmountForEmployment( (EmploymentCode)any );
			result = Optional.of(forEmployment);
		}};

		// Execute
		val result = CriterionAmountForEmployeeGettingService
						.get( require, new EmployeeId("EmpId"), GeneralDate.today() );

		// Assertion
		assertThat( result ).isEqualTo( forEmployment.getCriterionAmount() );
		assertThat( result ).isNotEqualTo( forCompany.getCriterionAmount() );

	}

	/**
	 * Target	: get
	 * Pattern	: 雇用別の目安金額 利用する
	 * 				&& 社員の雇用期間 なし
	 * @param forCompany 会社の目安金額
	 */
	@Test
	public void test_get_empHistIsNotExists() {

		// 目安利用区分 雇用別＝使用する
		val usageStg = CriterionAmountHelper.createUsageSetting("CmpId", true);

		new Expectations() {{
			// 目安利用区分
			require.getUsageSetting();
			result = Optional.of(usageStg);
			// 社員の雇用期間
			require.getEmploymentHistory( (EmployeeId)any, (GeneralDate)any );
		}};

		// Assertion
		NtsAssert.businessException( "Msg_426"
				, () -> CriterionAmountForEmployeeGettingService
							.get( require, new EmployeeId("EmpId"), GeneralDate.today() ));

	}

}
