package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class EstimateAmountListTest {

	@Test
	public void getters() {
		val estimateAmount = EstimateAmountList.create(Arrays.asList(
				  new EstimateAmountByCondition(new EstimateAmountNo(1), new EstimateAmount(500))
				, new EstimateAmountByCondition(new EstimateAmountNo(2), new EstimateAmount(600))
				, new EstimateAmountByCondition(new EstimateAmountNo(3), new EstimateAmount(700))
			));
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


	/**
	 * Target	: getEstimateAmountByNo
	 */
	@Test
	public void test_getEstimateAmountByNo() {

		// 目安金額リスト
		val amountList = EstimateAmountList.create(
				IntStream.rangeClosed( 1, 3 ).boxed()
					.map( no -> new EstimateAmountByCondition( new EstimateAmountNo( no ), new EstimateAmount( no * 500 ) ) )
					.collect(Collectors.toList())
			);

		// 該当枠Noあり
		val resultNo3 = amountList.getEstimateAmountByNo( new EstimateAmountNo( 3 ) );
		assertThat( resultNo3 ).isPresent();
		assertThat( resultNo3.get().getEstimateAmountNo().v() ).isEqualTo( 3 );
		assertThat( resultNo3.get().getEstimateAmount() ).isEqualTo( new EstimateAmount( 3 * 500 ) );

		// 該当枠Noなし
		val resultNo4 = amountList.getEstimateAmountByNo( new EstimateAmountNo( 4 ) );
		assertThat( resultNo4 ).isEmpty();

	}


	/**
	 * Target	: getStepOfEstimateAmount
	 * @param require require
	 */
	@Test
	public void test_getStepOfEstimateAmount(@Injectable EstimateAmountList.Require require) {

		// 目安金額リスト
		val instance = EstimateAmountList.create(Arrays.asList(
						EstimateAmountHelper.createAmountPerFrame( 1, 1000 )
					,	EstimateAmountHelper.createAmountPerFrame( 2, 1200 )
					,	EstimateAmountHelper.createAmountPerFrame( 3, 1500 )
					,	EstimateAmountHelper.createAmountPerFrame( 4, 2000 )
				));
		// 期待値
		@SuppressWarnings("serial")
		val expected = new HashMap<Integer, StepOfEstimateAmount>() {{

			put(  999, EstimateAmountHelper.createStep(require, 1,    0, Optional.of(1000)) );
			put( 1200, EstimateAmountHelper.createStep(require, 3, 1200, Optional.of(1500)) );
			put( 1549, EstimateAmountHelper.createStep(require, 4, 1500, Optional.of(2000)) );
			put( 2974, EstimateAmountHelper.createStep(require, 4, 2000, Optional.empty()) );

		}};


		// Execute
		val result = expected.keySet().stream()
				.collect(Collectors.toMap( e -> e
								, e -> instance.getStepOfEstimateAmount( require, new EstimateAmount( e ) )
							));


		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}

}
