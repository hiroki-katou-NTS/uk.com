package nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * Test for LaborCostBudgetRegisterService
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class LaborCostBudgetRegisterServiceTest {

	@Injectable LaborCostBudgetRegisterService.Require require;


	/**
	 * Target	: regist
	 * Pattern	: 予算 is empty
	 * Expected	: deleteのみ実行
	 */
	@Test
	public void test_regist_amountIsEmpty() {

		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("WkpId");
		val ymd = GeneralDate.today();

		// Execute & assert
		NtsAssert.atomTask(
				() -> LaborCostBudgetRegisterService.regist(require, targetOrg, ymd, Optional.empty())
			,	any -> require.delete(targetOrg, ymd)
		);

	}

	/**
	 * Target	: regist
	 * Pattern	: 予算 is not empty
	 * Expected	: delete＋insert実行
	 */
	@Test
	public void test_regist_amountIsNotEmpty() {

		val budget = new LaborCostBudget(
								TargetOrgIdenInfor.creatIdentifiWorkplace("WkpId")
							,	GeneralDate.today()
							,	new LaborCostBudgetAmount( 1280 )
						);

		// Execute & assert
		NtsAssert.atomTask(
				() -> LaborCostBudgetRegisterService.regist(require, budget.getTargetOrg(), budget.getYmd(), Optional.of(budget.getAmount()))
			,	any -> require.delete(budget.getTargetOrg(), budget.getYmd())
			,	any -> require.insert(budget)
		);

	}

}
