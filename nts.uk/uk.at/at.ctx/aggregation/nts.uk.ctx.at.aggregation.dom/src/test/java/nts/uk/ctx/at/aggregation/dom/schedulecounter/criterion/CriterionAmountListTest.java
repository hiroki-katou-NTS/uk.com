package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class CriterionAmountListTest {


	@Injectable CriterionAmountList.Require require;


	@Test
	public void getters() {
		val estimateAmount = CriterionAmountList.create(Arrays.asList(
				  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
				, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(600))
				, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(700))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(600))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(700))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(700))
						, new CriterionAmountByNo(new CriterionAmountNo(4), new CriterionAmountValue(700))
						, new CriterionAmountByNo(new CriterionAmountNo(5), new CriterionAmountValue(800))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(600))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(600))
						, new CriterionAmountByNo(new CriterionAmountNo(4), new CriterionAmountValue(600))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(600))
						, new CriterionAmountByNo(new CriterionAmountNo(4), new CriterionAmountValue(700))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(3), new CriterionAmountValue(600))
						, new CriterionAmountByNo(new CriterionAmountNo(4), new CriterionAmountValue(700))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(500))
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
			CriterionAmountList.create(Arrays.asList(
						  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
						, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(499))
					));
		});
	}

	/**
	 * input: 目安金額リスト[N].金額  < 目安金額リスト[N+1].金額
	 * output: エラーではない
	 */
	@Test
	public void create_estimateAmounts_success() {
		val estimatePrise = CriterionAmountList.create(Arrays.asList(
					  new CriterionAmountByNo(new CriterionAmountNo(1), new CriterionAmountValue(500))
					, new CriterionAmountByNo(new CriterionAmountNo(2), new CriterionAmountValue(501))
				));

		assertThat(estimatePrise.getList())
						.extracting(d->d.getFrameNo().v(), d->d.getAmount().v())
						.containsExactly( tuple(1, 500)
										, tuple(2, 501));
	}


	/**
	 * Target	: getEstimateAmountByNo
	 */
	@Test
	public void test_getEstimateAmountByNo() {

		// 目安金額リスト
		val amountList = CriterionAmountList.create(Arrays.asList(
				CriterionAmountHelper.createAmountPerFrame( 1,  500 )
			,	CriterionAmountHelper.createAmountPerFrame( 2, 1000 )
			,	CriterionAmountHelper.createAmountPerFrame( 3, 1500 )
		));


		// 該当枠NOあり
		{
			// 枠NO
			val no = new CriterionAmountNo( 3 );

			// Execute
			val result = amountList.getCriterionAmountByNo( no );

			// Assertion
			assertThat( result ).isPresent();
			assertThat( result.get().getFrameNo() ).isEqualTo( no );
			assertThat( result.get().getAmount() ).isEqualTo( new CriterionAmountValue( 1500 ) );
		}


		// 該当枠NOなし
		{
			// Execute
			val result = amountList.getCriterionAmountByNo( new CriterionAmountNo( 4 ) );

			// Assertion
			assertThat( result ).isEmpty();
		}

	}


	/**
	 * Target	: getStepOfEstimateAmount
	 * @param require require
	 */
	@Test
	public void test_getStepOfEstimateAmount(@Injectable CriterionAmountList.Require req4test) {

		// 目安金額の扱い
		val handling = CriterionAmountHelper.createHandling(1);
		// 目安金額リスト
		val instance = CriterionAmountList.create(Arrays.asList(
						CriterionAmountHelper.createAmountPerFrame( 1, 1000 )
					,	CriterionAmountHelper.createAmountPerFrame( 2, 1200 )
					,	CriterionAmountHelper.createAmountPerFrame( 3, 1500 )
					,	CriterionAmountHelper.createAmountPerFrame( 4, 2000 )
				));
		// Mockup設定
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(require, handling);
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(req4test, handling);


		// 期待値
		@SuppressWarnings("serial")
		val expected = new HashMap<Integer, StepOfCriterionAmount>() {{
			// Key: 値, Value: 取得対象の『目安金額の段階』
			put(  999, CriterionAmountHelper.createStep(req4test, 1,    0, Optional.of(1000)) );
			put( 1200, CriterionAmountHelper.createStep(req4test, 3, 1200, Optional.of(1500)) );
			put( 1549, CriterionAmountHelper.createStep(req4test, 4, 1500, Optional.of(2000)) );
			put( 2974, CriterionAmountHelper.createStep(req4test, 4, 2000, Optional.empty()) );
		}};


		// Execute
		val result = expected.entrySet().stream()
				.collect(Collectors.toMap( Map.Entry::getKey
								, entry -> instance.getStepOfEstimateAmount( require, new CriterionAmountValue( entry.getKey() ) )
							));


		// Assertion
		assertThat( result ).containsExactlyInAnyOrderEntriesOf( expected );

	}

}
