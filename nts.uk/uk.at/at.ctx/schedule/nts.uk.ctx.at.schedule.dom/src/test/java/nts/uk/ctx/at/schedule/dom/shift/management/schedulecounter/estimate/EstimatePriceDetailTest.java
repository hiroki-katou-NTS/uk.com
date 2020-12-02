package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class EstimatePriceDetailTest {
	@Test
	public void getters() {
		val estimatePrices = Helper.createEstimatePriceDetail();
		NtsAssert.invokeGetters(estimatePrices);
	}
	
	@Test
	public void create_estimatePrices_success() {
		val yearEstimatePrice = EstimatePrices.create(Arrays.asList(
				  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				));
		
		val monthEstimatePrice = EstimatePrices.create(Arrays.asList(
				  EstimateAmountByCondition.create(new EstimateAmountNo(1), 1000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(2), 2000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(3), 3000)
				));
		
		val estimatePriceDetail = new EstimatePriceDetail(yearEstimatePrice, monthEstimatePrice);
		
		assertThat(estimatePriceDetail.getYearEstimatePrice()).isEqualTo(yearEstimatePrice);
		assertThat(estimatePriceDetail.getMonthEstimatePrice()).isEqualTo(monthEstimatePrice);
		
	}
	
	public static class Helper{
		
		public static EstimatePriceDetail createEstimatePriceDetail() {
			val yearEstimatePrice = createYearEstimatePrices();
			val monthEstimatePrice = createMonthEstimatePrices();
			return new EstimatePriceDetail(yearEstimatePrice, monthEstimatePrice);
		}
		
		public static EstimatePrices createYearEstimatePrices() {
			return EstimatePrices.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
					));
		}
		
		public static EstimatePrices createMonthEstimatePrices() {
			return EstimatePrices.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 1000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), 2000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), 3000)
					));
		}
	}
}
