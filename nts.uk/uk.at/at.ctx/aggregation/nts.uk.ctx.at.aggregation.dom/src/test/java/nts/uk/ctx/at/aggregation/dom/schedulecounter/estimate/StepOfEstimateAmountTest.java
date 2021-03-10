package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.color.ColorCode;

/**
 * Test for StepOfEstimateAmount
 * @author kumiko_otake
 */
public class StepOfEstimateAmountTest {

	@Injectable StepOfEstimateAmount.Require require;


	@Test
	public void test_getters() {
		val instance = EstimateAmountHelper.createStep(require, 3, 1200, Optional.of(1500));
		assertThat( instance.getNo().v() ).isEqualTo( 3 );
		NtsAssert.invokeGetters( instance );
	}


	/**
	 * Target	: create( require, no, exceeded, unexceeded )
	 * Pattern	: 通常
	 */
	@Test
	public void test_create_with_eachParams_complete() {

		// 目安金額の扱い：1, 3, 4 のみ
		Helper.setHandling( require, 1, 3, 4 );


		// 目安枠NO=3
		{
			// Execute
			val result = StepOfEstimateAmount.create( require
						, new EstimateAmountNo( 3 )					// 枠NO
						, new EstimateAmount( 2000 )				// 超過済み金額
						, Optional.of( new EstimateAmount( 2500 ) )	// 未超過金額
					);

			// Assertion
			assertThat( result.getNo() ).isEqualTo( new EstimateAmountNo( 3 ) );
			assertThat( result.getExceeded() ).isEqualTo( new EstimateAmount( 2000 ) );
			assertThat( result.getUnexceeded() ).isPresent()
							.get().isEqualTo( new EstimateAmount( 2500 ) );
			assertThat( result.getBackground() ).isPresent()
							.get().isEqualTo( new ColorCode( "#333333" ) );
		}


		// 目安枠NO=5
		{
			// Execute
			val result = StepOfEstimateAmount.create( require
						, new EstimateAmountNo( 5 )		// 枠NO
						, new EstimateAmount( 3000 )	// 超過済み金額
						, Optional.empty()				// 未超過金額
					);

			// Assertion
			assertThat( result.getNo() ).isEqualTo( new EstimateAmountNo( 5 ) );
			assertThat( result.getExceeded() ).isEqualTo( new EstimateAmount( 3000 ) );
			assertThat( result.getUnexceeded() ).isEmpty();
			assertThat( result.getBackground() ).isEmpty();
		}

	}

	/**
	 * Target	: create( require, no, exceeded, unexceeded )
	 * Pattern	: 不変条件違反
	 */
	@Test
	public void test_create_with_eachParams_error() {

		// 目安金額の扱い
		Helper.setHandling( require, 1 );
		// 目安金額
		val amountValue = new AtomicInteger( 1000 );

		// Assertion: 超過金額＜未超過金額⇒正常終了
		StepOfEstimateAmount.create( require
				, new EstimateAmountNo( 1 )
				, new EstimateAmount( amountValue.get() )
				, Optional.of( new EstimateAmount( amountValue.incrementAndGet() ) )
			);

		// Assertion: 超過金額＝未超過金額⇒システムエラー
		NtsAssert.systemError(() -> StepOfEstimateAmount.create( require
				, new EstimateAmountNo( 1 )
				, new EstimateAmount( amountValue.get() )
				, Optional.of( new EstimateAmount( amountValue.get() ) )
			));

		// Assertion: 超過金額＞未超過金額⇒システムエラー
		NtsAssert.systemError(() -> StepOfEstimateAmount.create( require
				, new EstimateAmountNo( 1 )
				, new EstimateAmount( amountValue.get() )
				, Optional.of( new EstimateAmount( amountValue.decrementAndGet() ) )
			));

	}


	/**
	 * Target	: create( require, exceeded, unexceeded )
	 */
	@Test
	public void test_create_with_byCondition() {

		// 目安金額の扱い
		Helper.setHandling( require, 1 );

		// Execute
		val result = StepOfEstimateAmount.createFromCondition( require
				, EstimateAmountHelper.createAmountPerFrame( 3, 1800 )
				, EstimateAmountHelper.createAmountPerFrame( 4, 2000 )
			);

		// Assertion
		assertThat( result.getNo() ).isEqualTo( new EstimateAmountNo( 4 ) );
		assertThat( result.getExceeded() ).isEqualTo( new EstimateAmount( 1800 ) );
		assertThat( result.getUnexceeded() ).isPresent()
						.get().isEqualTo( new EstimateAmount( 2000 ) );

		assertThat( result.getBackground() ).isEmpty();

	}


	/**
	 * Target	: createWithoutExceeded
	 */
	@Test
	public void test_createWithoutExceeded() {

		// 目安金額の扱い
		Helper.setHandling( require, 3 );

		// Execute
		val result = StepOfEstimateAmount.createWithoutExceeded( require
				, EstimateAmountHelper.createAmountPerFrame( 1, 300 )
			);

		// Assertion
		assertThat( result.getNo() ).isEqualTo( new EstimateAmountNo( 1 ) );
		assertThat( result.getExceeded() ).isEqualTo( new EstimateAmount( 0 ) );
		assertThat( result.getUnexceeded() ).isPresent()
						.get().isEqualTo( new EstimateAmount( 300 ) );
		assertThat( result.getBackground() ).isEmpty();

	}


	/**
	 * Target	: createWithoutUnexceeded
	 */
	@Test
	public void test_createWithoutUnexceeded() {

		// 目安金額の扱い
		Helper.setHandling( require, 3 );

		// Execute
		val result = StepOfEstimateAmount.createWithoutUnexceeded( require
				, EstimateAmountHelper.createAmountPerFrame( 2, 300 )
			);

		// Assertion
		assertThat( result.getNo() ).isEqualTo( new EstimateAmountNo( 2 ) );
		assertThat( result.getExceeded() ).isEqualTo( new EstimateAmount( 300 ) );
		assertThat( result.getUnexceeded() ).isEmpty();
		assertThat( result.getBackground() ).isEmpty();

	}


	/**
	 * Target	: getStandardAmount, isExceedAll
	 * Pattern	: 未超過金額あり
	 */
	@Test
	public void test_getStandardAmount_isExceedAll_uneceededIsPresent() {

		// 目安金額の扱い
		Helper.setHandling( require, 3 );

		// 目安金額の段階
		val instance = StepOfEstimateAmount.create( require
				, new EstimateAmountNo( 2 )
				, new EstimateAmount( 1200 )
				, Optional.of(new EstimateAmount( 1500 ))
			);

		// Assertion
		assertThat( instance.getStandardAmount() ).isEqualTo( instance.getUnexceeded().get() );
		assertThat( instance.isExceedAll() ).isFalse();

	}

	/**
	 * Target	: getStandardAmount, isExceedAll
	 * Pattern	: 未超過金額なし
	 */
	@Test
	public void test_getStandardAmount_isExceedAll_uneceededIsEmpty() {

		// 目安金額の扱い
		Helper.setHandling( require, 3 );

		// 目安金額の段階
		val instance = StepOfEstimateAmount.create( require
				, new EstimateAmountNo( 5 )
				, new EstimateAmount( 3000 )
				, Optional.empty()
			);

		// Assertion
		assertThat( instance.getStandardAmount() ).isEqualTo( instance.getExceeded() );
		assertThat( instance.isExceedAll() ).isTrue();

	}


	public static class Helper {

		/**
		 * Requireに目安金額の扱いを設定する
		 * @param require
		 * @param frameNo
		 */
		public static <T extends StepOfEstimateAmount.Require> void setHandling(T require, int...frameNo) {

			val handling = EstimateAmountHelper.createHandling(frameNo);

			new Expectations() {{
				require.getHandling();
				result = handling;
			}};

		}

	}
}
