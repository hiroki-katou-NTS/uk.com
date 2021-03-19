package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
@RunWith(JMockit.class)
public class EstimateAmountForCompanyTest {
	
	@Test
	public void getters() {
		val ceAmount = new EstimateAmountForCompany(Helper.createEstDetailPrice());
		NtsAssert.invokeGetters(ceAmount);
	}
	
	@Test
	public void testUpdate() {
		
		val detail = Helper.createEstDetailPrice();
        val ceAmount = new EstimateAmountForCompany(detail);
        
        val newDetail = new EstimateAmountDetail(
				EstimateAmountList.create(Arrays.asList(new EstimateAmountByCondition(new EstimateAmountNo(1),  new EstimateAmount(90000)))),
				EstimateAmountList.create(Arrays.asList(new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(1000))))
				);
        
        ceAmount.update(newDetail);
        
		assertThat(ceAmount.getDetail()).isEqualTo(newDetail);
	}
	
	public static class Helper {
		
		public static EstimateAmountDetail createEstDetailPrice() {
			return new EstimateAmountDetail(Helper.createYearEstAmount(), Helper.createMonthEstAmount());
		}

		public static EstimateAmountList createYearEstAmount() {
			return EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1),  new EstimateAmount(90000))
					, new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(100000))
					, new EstimateAmountByCondition(new EstimateAmountNo(3),  new EstimateAmount(110000))
					));
		}

		public static EstimateAmountList createMonthEstAmount() {
			return EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(1000))
					, new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(2000))
					, new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(3000))
					));
		}

	}

}
