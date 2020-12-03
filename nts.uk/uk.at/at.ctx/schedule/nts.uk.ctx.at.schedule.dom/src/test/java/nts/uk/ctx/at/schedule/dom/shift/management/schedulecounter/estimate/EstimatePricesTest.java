package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class EstimatePricesTest {
	@Test
	public void getters() {
		val estimatePrices = Helper.createEstimatePrices();
		NtsAssert.invokeGetters(estimatePrices);
	}
	
	/**
	 * input: 	目安金額リスト.size() ＞ 5		
	 * output: Msg_1869
	 *
	 */
	@Test
	public void create_estimatePrices_Msg_1869() {
		NtsAssert.businessException("Msg_1869", () -> {
			EstimateAmounts.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(4), 110000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(5), 110000)
				    
					)
			);
		});
	}
	
	/**
	 * input: 目安金額リストの位置番号が重複します
	 * output: Msg_1870
	 *
	 */
	@Test
	public void create_estimatePrices_Msg_1870() {
		NtsAssert.businessException("Msg_1870", () -> {
			EstimateAmounts.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(4), 110000)
					));
		});
	}
	
	/**
	 * input: 目安金額リストの位置番号が重複します
	 * output: Msg_1871
	 *
	 */
	@Test
	public void create_estimatePrices_Msg_1871() {
		NtsAssert.businessException("Msg_1871", () -> {
			EstimateAmounts.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 100000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(2), 110000)
					));
		});
	}
	
	/**
	 * input: 目安金額リスト[N].金額 > 目安金額リスト[N+1].金額
	 * output: Msg_147
	 *
	 */
	@Test
	public void create_estimatePrices_Msg_147() {
		NtsAssert.businessException("Msg_147", () -> {
			EstimateAmounts.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(2), 120000)
				    , EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
					));
		});
	}
	
	/*** 作成する  */
	@Test
	public void create_estimatePrices_success() {
		val estimatePrise = EstimateAmounts.create(Arrays.asList(
				  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
				, EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
				));
		
		assertThat(estimatePrise.getEstimatePrices())
						.extracting(d->d.getEstimateAmountNo().v(), d->d.getEstimateAmount().v())
						.containsExactly( tuple(1, 90000)
										, tuple(2, 100000)
										, tuple(3, 110000));
	}
	
	public static class Helper{
		
		public static EstimateAmounts createEstimatePrices() {
			return EstimateAmounts.create(Arrays.asList(
					  EstimateAmountByCondition.create(new EstimateAmountNo(1), 90000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(2), 100000)
					, EstimateAmountByCondition.create(new EstimateAmountNo(3), 110000)
					));
		}
	}
}
