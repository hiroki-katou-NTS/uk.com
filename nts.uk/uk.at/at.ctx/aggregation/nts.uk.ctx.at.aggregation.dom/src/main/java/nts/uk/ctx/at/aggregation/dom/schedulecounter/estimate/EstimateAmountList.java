package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 目安金額リスト
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.目安金額リスト
 * @author lan_lt
 */
@Value
public class EstimateAmountList implements DomainValue {

	/**　目安金額リスト　*/
	private final List<EstimateAmountByCondition> estimatePrices;


	/**
	 * 作る
	 * @param estimateAmount 目安金額リスト
	 * @return
	 */
	public static EstimateAmountList create(List<EstimateAmountByCondition> estimateAmount) {
		if(estimateAmount.size() > 5) {
			throw new BusinessException("Msg_1869");
		}

		val estimateDistincts = estimateAmount.stream().map(c -> c.getEstimateAmountNo().v())
													   .distinct().collect(Collectors.toList());
		if(estimateAmount.size() > estimateDistincts.size()) {
			throw new BusinessException("Msg_1870");
		}

		if(!estimateAmount.isEmpty() && estimateAmount.get(0).getEstimateAmountNo().v() != 1 ) {
			throw new BusinessException("Msg_1871");
		}

		estimateAmount.stream().reduce((pre, next) -> {

			if (pre.getEstimateAmountNo().v() != (next.getEstimateAmountNo().v() - 1)) {
				throw new BusinessException("Msg_1871");
			}

			if (pre.getEstimateAmount().v() >= next.getEstimateAmount().v()) {
				throw new BusinessException("Msg_147");
			}

			return next;
		});

		return new EstimateAmountList(estimateAmount);
	}


	/**
	 * 枠NOを指定して目安金額を取得する
	 * @param no 枠NO
	 * @return
	 */
	public Optional<EstimateAmountByCondition> getEstimateAmountByNo(EstimateAmountNo no) {
		return this.estimatePrices.stream()
					.filter( e -> e.getEstimateAmountNo().equals( no ) )
					.findFirst();
	}

	/**
	 * 目安金額の段階を判定する
	 * @param require require
	 * @param target 判定対象金額
	 * @return 目安金額の段階
	 */
	public StepOfEstimateAmount getStepOfEstimateAmount(Require require, EstimateAmount target) {

		// 枠NO順にソート
		val sortedPrices = this.estimatePrices.stream()
				.sorted(Comparator.comparing( EstimateAmountByCondition::getEstimateAmountNo ))
				.collect(Collectors.toList());


		// 未超過金額を取得
		val unexceeded = sortedPrices.stream()
				.filter( e -> e.getEstimateAmount().greaterThan( target ) )
				.findFirst();

		if( !unexceeded.isPresent() ) {
			// 未超過金額なし
			return StepOfEstimateAmount.createWithoutUnexceeded( require, sortedPrices.get(sortedPrices.size()-1) );
		} else if( unexceeded.get().getEstimateAmountNo().v() == 1 ) {
			// 超過済み金額なし
			return StepOfEstimateAmount.createWithoutExceeded( require, unexceeded.get() );
		}


		// 超過済み金額を取得
		val exceeded = sortedPrices.stream()
				.filter( e -> e.getEstimateAmountNo().v() == (unexceeded.get().getEstimateAmountNo().v() - 1) )
				.findFirst().get();
		return StepOfEstimateAmount.createFromCondition( require, exceeded, unexceeded.get() );

	}



	public static interface Require
			extends StepOfEstimateAmount.Require {
	}

}
