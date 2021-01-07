package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@RunWith(JMockit.class)
public class EstimateAmountForEmploymentTest {
	@Test
	public void getters() {
		val detail = Helper.createEstDetailPrice();
		val eeAmount = new EstimateAmountForEmployment(new EmploymentCode("01"), detail);
		NtsAssert.invokeGetters(eeAmount);
	}
	
	@Test
	public void testUpdate() {
		
		val detail = Helper.createEstDetailPrice();
        val eeAmount = new EstimateAmountForEmployment(new EmploymentCode("01"), detail);
        
        val newDetail = new EstimateAmountDetail(
				EstimateAmountList.create(Arrays.asList(new EstimateAmountByCondition(new EstimateAmountNo(1),  new EstimateAmount(90000)))),
				EstimateAmountList.create(Arrays.asList(new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(1000))))
				);
        
        eeAmount.update(newDetail);
        
		assertThat(eeAmount.getDetail()).isEqualTo(newDetail);
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
