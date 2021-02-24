package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

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
public class StepOfEstimateAmount {

	/** 枠NO **/
	private final EstimateAmountNo no;
	/** 超過済み金額 **/
	private final EstimateAmount exceeded;
	/** 未超過金額 **/
	private final Optional<EstimateAmount> unexceeded;
	/** 背景色 **/
	private final Optional<ColorCode> background;


	/**
	 * 個別に指定して作成する
	 * @param require require
	 * @param no 枠NO
	 * @param exceeded 超過済み金額
	 * @param unexceeded 未超過金額
	 * @return 目安金額の段階
	 */
	public static StepOfEstimateAmount create(Require require
			, EstimateAmountNo no
			, EstimateAmount exceeded
			, Optional<EstimateAmount> unexceeded
	) {

		// 不変条件：超過済み金額＜未超過金額
		if( unexceeded.isPresent() && unexceeded.get().lessThanOrEqualTo( exceeded ) ) {
			throw new RuntimeException("Exceeded amount is over unexceeded amount");
		}

		// 該当枠の設定を取得する
		// ※『目安金額の扱い』は初期データのため常に存在する
		val handling = require.getHandling().get();
		val background = handling.getHandleFrameNoList().stream()
								.filter( e -> e.getEstimateAmountNo().v() == no.v() )
								.findFirst()
									.map( o -> o.getBackgroundColor() );

		return new StepOfEstimateAmount( no, exceeded, unexceeded, background );

	}

	/**
	 * 目安金額を指定して作成する
	 * @param require require
	 * @param exceeded 超過済みの目安金額
	 * @param unexceeded 未超過の目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfEstimateAmount create(Require require, EstimateAmountByCondition exceeded, EstimateAmountByCondition unexceeded) {

		return StepOfEstimateAmount.create( require
					, unexceeded.getEstimateAmountNo()
					, exceeded.getEstimateAmount()
					, Optional.of( unexceeded.getEstimateAmount() )
				);

	}

	/**
	 * 超過済み金額なしで作成する
	 * @param require require
	 * @param unexceeded 未超過の目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfEstimateAmount createWithoutExceeded(Require require, EstimateAmountByCondition unexceeded) {

		return StepOfEstimateAmount.create( require
					, unexceeded.getEstimateAmountNo()
					, new EstimateAmount( 0 )
					, Optional.of( unexceeded.getEstimateAmount() )
				);

	}

	/**
	 * 未超過金額なしで作成する
	 * @param require require
	 * @param exceeded 超過済みの目安金額
	 * @return 目安金額の段階
	 */
	public static StepOfEstimateAmount createWithoutUnexceeded(Require require, EstimateAmountByCondition exceeded) {

		return StepOfEstimateAmount.create( require
					, exceeded.getEstimateAmountNo()
					, exceeded.getEstimateAmount()
					, Optional.empty()
				);

	}


	/**
	 * 基準の金額を取得する
	 * @return 基準となる目安金額
	 */
	public EstimateAmount getStandardAmount() {

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
		public Optional<HandingOfEstimateAmount> getHandling();

	}

}
