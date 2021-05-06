package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.shr.com.color.ColorCode;

/**
 * 目安金額の段階
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.目安金額の段階
 * @author kumiko_otake
 */
@Value
public class StepOfCriterionAmount {

	/** 枠NO **/
	private final CriterionAmountNo frameNo;
	/** 超過済み金額 **/
	private final CriterionAmountValue exceeded;
	/** 未超過金額 **/
	private final Optional<CriterionAmountValue> unexceeded;
	/** 背景色 **/
	private final Optional<ColorCode> background;


	/**
	 * 個別に指定して作成する
	 * @param require require
	 * @param frameNo 枠NO
	 * @param exceeded 超過済み金額
	 * @param unexceeded 未超過金額
	 * @return 目安金額の段階
	 */
	public static StepOfCriterionAmount create(Require require
			, CriterionAmountNo frameNo
			, CriterionAmountValue exceeded
			, Optional<CriterionAmountValue> unexceeded
	) {

		// 不変条件：超過済み金額＜未超過金額
		if( unexceeded.isPresent() && unexceeded.get().lessThanOrEqualTo( exceeded ) ) {
			throw new RuntimeException("Exceeded amount is over unexceeded amount");
		}

		// 該当枠の設定を取得する
		// ※『目安金額の扱い』は初期データのため常に存在する
		val handling = require.getHandling().get();
		val background = handling.getList().stream()
								.filter( e -> e.getFrameNo().equals( frameNo ) )
								.findFirst()
									.map( o -> o.getBackgroundColor() );

		return new StepOfCriterionAmount( frameNo, exceeded, unexceeded, background );

	}

	/**
	 * 目安金額を指定して作成する
	 * @param require require
	 * @param exceeded 超過済みの目安金額
	 * @param unexceeded 未超過の目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfCriterionAmount createFromCondition(Require require
			, CriterionAmountByNo exceeded, CriterionAmountByNo unexceeded
	) {

		return StepOfCriterionAmount.create( require
					, unexceeded.getFrameNo()
					, exceeded.getAmount()
					, Optional.of( unexceeded.getAmount() )
				);

	}

	/**
	 * 超過済み金額なしで作成する
	 * @param require require
	 * @param unexceeded 未超過の目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfCriterionAmount createWithoutExceeded(Require require, CriterionAmountByNo unexceeded) {

		return StepOfCriterionAmount.create( require
					, unexceeded.getFrameNo()
					, new CriterionAmountValue( 0 )
					, Optional.of( unexceeded.getAmount() )
				);

	}

	/**
	 * 未超過金額なしで作成する
	 * @param require require
	 * @param exceeded 超過済みの目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfCriterionAmount createWithoutUnexceeded(Require require, CriterionAmountByNo exceeded) {

		return StepOfCriterionAmount.create( require
					, exceeded.getFrameNo()
					, exceeded.getAmount()
					, Optional.empty()
				);

	}


	/**
	 * 基準の金額を取得する
	 * @return 基準となる目安金額
	 */
	public CriterionAmountValue getCriterionAmount() {

		// 未超過金額がなければ超過済み金額を返す
		return this.unexceeded.orElse( this.exceeded );

	}

	/**
	 * すべての目安金額を超過しているか
	 * @return
	 */
	public boolean isExceedAll() {

		// 未超過金額がない⇒すべての目安金額を超過している
		return !this.unexceeded.isPresent();

	}



	public static interface Require {

		/**
		 * 目安金額の扱いを取得する
		 * @return 目安金額の扱い
		 */
		public Optional<HandlingOfCriterionAmount> getHandling();

	}

}
