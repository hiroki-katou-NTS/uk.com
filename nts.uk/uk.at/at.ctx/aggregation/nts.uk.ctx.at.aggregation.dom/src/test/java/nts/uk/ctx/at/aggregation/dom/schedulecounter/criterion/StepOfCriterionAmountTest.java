package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.color.ColorCode;

/**
 * Test for StepOfEstimateAmount
 * @author kumiko_otake
 */
@RunWith(JMockit.class)
public class StepOfCriterionAmountTest {

	@Injectable StepOfCriterionAmount.Require require;


	@Test
	public void test_getters() {
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(require, CriterionAmountHelper.createHandling(1));
		val instance = CriterionAmountHelper.createStep(require, 3, 1200, Optional.of(1500));
		NtsAssert.invokeGetters( instance );
	}


	/**
	 * Target	: create( require, frameNo, exceeded, unexceeded )
	 * Pattern	: 通常
	 */
	@Test
	public void test_create_with_eachParams_complete() {

		// 目安金額の扱い：1, 3, 4 のみ
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
					require
				,	CriterionAmountHelper.createHandling(1, 3, 4)
			);


		// 目安枠NO=3
		{
			// Execute
			val result = StepOfCriterionAmount.create( require
						, new CriterionAmountNo( 3 )						// 枠NO
						, new CriterionAmountValue( 2000 )					// 超過済み金額
						, Optional.of( new CriterionAmountValue( 2500 ) )	// 未超過金額
					);

			// Assertion
			assertThat( result.getFrameNo() ).isEqualTo( new CriterionAmountNo( 3 ) );
			assertThat( result.getExceeded() ).isEqualTo( new CriterionAmountValue( 2000 ) );
			assertThat( result.getUnexceeded() ).isPresent()
							.get().isEqualTo( new CriterionAmountValue( 2500 ) );
			assertThat( result.getBackground() ).isPresent()
							.get().isEqualTo( new ColorCode( "#333333" ) );
		}


		// 目安枠NO=5
		{
			// Execute
			val result = StepOfCriterionAmount.create( require
						, new CriterionAmountNo( 5 )		// 枠NO
						, new CriterionAmountValue( 3000 )	// 超過済み金額
						, Optional.empty()					// 未超過金額
					);

			// Assertion
			assertThat( result.getFrameNo() ).isEqualTo( new CriterionAmountNo( 5 ) );
			assertThat( result.getExceeded() ).isEqualTo( new CriterionAmountValue( 3000 ) );
			assertThat( result.getUnexceeded() ).isEmpty();
			assertThat( result.getBackground() ).isEmpty();
		}

	}

	/**
	 * Target	: create( require, frameNo, exceeded, unexceeded )
	 * Pattern	: 不変条件違反
	 */
	@Test
	public void test_create_with_eachParams_error() {

		// 目安金額の扱い
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(1)
		);
		// 目安金額
		val amountValue = new AtomicInteger( 1000 );

		// Assertion: 超過金額＜未超過金額⇒正常終了
		StepOfCriterionAmount.create( require
				, new CriterionAmountNo( 1 )
				, new CriterionAmountValue( amountValue.get() )
				, Optional.of( new CriterionAmountValue( amountValue.incrementAndGet() ) )
			);

		// Assertion: 超過金額＝未超過金額⇒システムエラー
		NtsAssert.systemError(() -> StepOfCriterionAmount.create( require
				, new CriterionAmountNo( 1 )
				, new CriterionAmountValue( amountValue.get() )
				, Optional.of( new CriterionAmountValue( amountValue.get() ) )
			));

		// Assertion: 超過金額＞未超過金額⇒システムエラー
		NtsAssert.systemError(() -> StepOfCriterionAmount.create( require
				, new CriterionAmountNo( 1 )
				, new CriterionAmountValue( amountValue.get() )
				, Optional.of( new CriterionAmountValue( amountValue.decrementAndGet() ) )
			));

	}


	/**
	 * Target	: create( require, exceeded, unexceeded )
	 */
	@Test
	public void test_create_with_byCondition() {

		// 目安金額の扱い
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(1)
		);

		// Execute
		val result = StepOfCriterionAmount.createFromCondition( require
				, CriterionAmountHelper.createAmountPerFrame( 3, 1800 )
				, CriterionAmountHelper.createAmountPerFrame( 4, 2000 )
			);

		// Assertion
		assertThat( result.getFrameNo() ).isEqualTo( new CriterionAmountNo( 4 ) );
		assertThat( result.getExceeded() ).isEqualTo( new CriterionAmountValue( 1800 ) );
		assertThat( result.getUnexceeded() ).isPresent()
						.get().isEqualTo( new CriterionAmountValue( 2000 ) );

		assertThat( result.getBackground() ).isEmpty();

	}


	/**
	 * Target	: createWithoutExceeded
	 */
	@Test
	public void test_createWithoutExceeded() {

		// 目安金額の扱い
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(3)
		);

		// Execute
		val result = StepOfCriterionAmount.createWithoutExceeded( require
				, CriterionAmountHelper.createAmountPerFrame( 1, 300 )
			);

		// Assertion
		assertThat( result.getFrameNo() ).isEqualTo( new CriterionAmountNo( 1 ) );
		assertThat( result.getExceeded() ).isEqualTo( new CriterionAmountValue( 0 ) );
		assertThat( result.getUnexceeded() ).isPresent()
						.get().isEqualTo( new CriterionAmountValue( 300 ) );
		assertThat( result.getBackground() ).isEmpty();

	}


	/**
	 * Target	: createWithoutUnexceeded
	 */
	@Test
	public void test_createWithoutUnexceeded() {

		// 目安金額の扱い
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(3)
		);

		// Execute
		val result = StepOfCriterionAmount.createWithoutUnexceeded( require
				, CriterionAmountHelper.createAmountPerFrame( 2, 300 )
			);

		// Assertion
		assertThat( result.getFrameNo() ).isEqualTo( new CriterionAmountNo( 2 ) );
		assertThat( result.getExceeded() ).isEqualTo( new CriterionAmountValue( 300 ) );
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
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(3)
		);

		// 目安金額の段階
		val instance = StepOfCriterionAmount.create( require
				, new CriterionAmountNo( 2 )
				, new CriterionAmountValue( 1200 )
				, Optional.of(new CriterionAmountValue( 1500 ))
			);

		// Assertion
		assertThat( instance.getCriterionAmount() ).isEqualTo( instance.getUnexceeded().get() );
		assertThat( instance.isExceedAll() ).isFalse();

	}

	/**
	 * Target	: getStandardAmount, isExceedAll
	 * Pattern	: 未超過金額なし
	 */
	@Test
	public void test_getStandardAmount_isExceedAll_uneceededIsEmpty() {

		// 目安金額の扱い
		CriterionAmountHelper.mockupRequireForStepOfCriterionAmount(
				require
			,	CriterionAmountHelper.createHandling(3)
		);

		// 目安金額の段階
		val instance = StepOfCriterionAmount.create( require
				, new CriterionAmountNo( 5 )
				, new CriterionAmountValue( 3000 )
				, Optional.empty()
			);

		// Assertion
		assertThat( instance.getCriterionAmount() ).isEqualTo( instance.getExceeded() );
		assertThat( instance.isExceedAll() ).isTrue();

	}

}
