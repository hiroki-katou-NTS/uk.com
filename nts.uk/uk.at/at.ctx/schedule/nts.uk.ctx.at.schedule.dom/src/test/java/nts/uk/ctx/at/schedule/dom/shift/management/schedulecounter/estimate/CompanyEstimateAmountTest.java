package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
@RunWith(JMockit.class)
public class CompanyEstimateAmountTest {
	
	@Test
	public void getters() {
		val ceAmount = Helper.createEstDetailPrice();
		NtsAssert.invokeGetters(ceAmount);
	}
	
	@Test
	public void testUpdate() {
		val detail = Helper.createEstDetailPrice();
        val ceAmount = new CompanyEstimateAmount();
        
        ceAmount.update(detail);
        
		assertThat(ceAmount.getDetail()).isEqualTo(detail);
	}
	
	public static class Helper {
		
		public static EstimateAmountDetail createEstDetailPrice() {
			return new EstimateAmountDetail(Helper.createYearEstAmount(), Helper.createMonthEstAmount());
		}

		public static EstimateAmounts createYearEstAmount() {
			return EstimateAmounts.create(Arrays.asList(EstimateAmountByCondition.create(
					  new EstimateAmountNo(1), 90000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
					));
		}

		public static EstimateAmounts createMonthEstAmount() {
			return EstimateAmounts.create(Arrays.asList(EstimateAmountByCondition.create(
					  new EstimateAmountNo(1), 1000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), 2000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), 3000)
					));
		}

	}

}
