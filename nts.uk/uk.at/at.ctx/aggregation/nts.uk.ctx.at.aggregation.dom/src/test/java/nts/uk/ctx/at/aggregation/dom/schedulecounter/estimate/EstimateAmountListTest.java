package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class EstimateAmountListTest {
	
	@Test
	public void getters() {
		val estimateAmount = Helper.createEstimatePrices();
		NtsAssert.invokeGetters(estimateAmount);
	}
	
	/**
	 * input: 	目安金額リスト.size() ＞ 5		
	 * output: Msg_1869
	 *
	 */
	@Test
	public void create_estimateAmounts_Msg_1869() {
		NtsAssert.businessException("Msg_1869", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(600))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(700))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(700))
				    , new EstimateAmountByCondition(new EstimateAmountNo(4), new EstimateAmount(700))
				    , new EstimateAmountByCondition(new EstimateAmountNo(5), new EstimateAmount(800))
				    
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
	public void create_estimateAmounts_Msg_1870() {
		NtsAssert.businessException("Msg_1870", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(600))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(600))
				    , new EstimateAmountByCondition(new EstimateAmountNo(4), new EstimateAmount(600))
					));
		});
	}
	
	/**
	 * input: 目安金額枠NO:　2，3，4のリスト ->　←1から始まらない
	 * output: Msg_1871
	 *
	 */
	@Test
	public void create_estimateAmounts_startNot1_Msg_1871() {
		NtsAssert.businessException("Msg_1871", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(600))
				    , new EstimateAmountByCondition(new EstimateAmountNo(4), new EstimateAmount(700))
					));
		});
	}
	
	/**
	 * input: 目安金額枠NO:　1，3，4のリスト ->　←連続ない
	 * output: Msg_1871
	 *
	 */
	@Test
	public void create_estimateAmounts_Not_Continuous_Msg_1871() {
		NtsAssert.businessException("Msg_1871", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(600))
				    , new EstimateAmountByCondition(new EstimateAmountNo(4), new EstimateAmount(700))
					));
		});
	}
	
	/**
	 * input: 目安金額リスト[N].金額 = 目安金額リスト[N+1].金額
	 * output: Msg_147
	 *
	 */
	@Test
	public void create_equal_amount_Msg_147() {
		NtsAssert.businessException("Msg_147", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(500))
					));
		});
	}
	
	/**
	 * input: 目安金額リスト[N].金額 > 目安金額リスト[N+1].金額
	 * output: Msg_147
	 *
	 */
	@Test
	public void create_more_amount_Msg_147() {
		NtsAssert.businessException("Msg_147", () -> {
			EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				    , new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(499))
					));
		});
	}
	
	/**
	 * input: 目安金額リスト[N].金額  < 目安金額リスト[N+1].金額
	 * output: エラーではない
	 */
	@Test
	public void create_estimateAmounts_success() {
		val estimatePrise = EstimateAmountList.create(Arrays.asList(
				  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				, new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(501))
				));
		
		assertThat(estimatePrise.getEstimatePrices())
						.extracting(d->d.getEstimateAmountNo().v(), d->d.getEstimateAmount().v())
						.containsExactly( tuple(1, 500)
										, tuple(2, 501));
	}
	
	public static class Helper{
		
		public static EstimateAmountList createEstimatePrices() {
			return EstimateAmountList.create(Arrays.asList(
					  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
					, new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(600))
					, new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(700))
					));
		}
	}
}
