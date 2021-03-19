package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
@RunWith(JMockit.class)
public class CriterionAmountForCompanyTest {

	@Test
	public void getters() {
		val ceAmount = new CriterionAmountForCompany(Helper.createEstDetailPrice());
		NtsAssert.invokeGetters(ceAmount);
	}

	@Test
	public void testUpdate() {

		val detail = Helper.createEstDetailPrice();
		val ceAmount = new CriterionAmountForCompany(detail);

		val newDetail = new CriterionAmount(
				CriterionAmountList.create(Arrays.asList(new CriterionAmountByNo(new CriterionAmountNo(1),  new CriterionAmountValue(90000)))),
				CriterionAmountList.create(Arrays.asList(new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(1000))))
				);

		ceAmount.update(newDetail);

		assertThat(ceAmount.getCriterionAmount()).isEqualTo(newDetail);
	}

	public static class Helper {

		public static CriterionAmount createEstDetailPrice() {
			return new CriterionAmount(Helper.createYearEstAmount(), Helper.createMonthEstAmount());
		}

		public static CriterionAmountList createYearEstAmount() {
			return CriterionAmountList.create(Arrays.asList(
					new CriterionAmountByNo(new CriterionAmountNo(1),  new CriterionAmountValue(90000))
					, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(100000))
					, new CriterionAmountByNo(new CriterionAmountNo(3),  new CriterionAmountValue(110000))
					));
		}

		public static CriterionAmountList createMonthEstAmount() {
			return CriterionAmountList.create(Arrays.asList(
					new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(1000))
					, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(2000))
					, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(3000))
					));
		}

	}

}
