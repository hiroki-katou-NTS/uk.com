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
        val ceAmount = new EstimateAmountForCompany();
        
        ceAmount.update(detail);
        
		assertThat(ceAmount.getDetail()).isEqualTo(detail);
	}
	
	public static class Helper {
		
		public static EstimateAmountDetail createEstDetailPrice() {
			return new EstimateAmountDetail(Helper.createYearEstAmount(), Helper.createMonthEstAmount());
		}

		public static EstimateAmountList createYearEstAmount() {
			return EstimateAmountList.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1),  new EstimateAmount(90000))
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), new EstimateAmount(100000))
					, EstimateAmountByCondition.create(new EstimateAmountNo(3),  new EstimateAmount(110000))
					));
		}

		public static EstimateAmountList createMonthEstAmount() {
			return EstimateAmountList.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), new EstimateAmount(1000))
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), new EstimateAmount(2000))
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), new EstimateAmount(3000))
					));
		}

	}

}
