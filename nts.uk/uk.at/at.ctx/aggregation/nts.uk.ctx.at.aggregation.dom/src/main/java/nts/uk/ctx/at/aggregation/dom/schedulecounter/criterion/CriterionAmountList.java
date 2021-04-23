package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

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
public class CriterionAmountList implements DomainValue {

	/** 目安金額リスト */
	private final List<CriterionAmountByNo> list;


	/**
	 * 作る
	 * @param list 目安金額リスト
	 * @return
	 */
	public static CriterionAmountList create(List<CriterionAmountByNo> list) {
		if(list.size() > 5) {
			throw new BusinessException("Msg_1869");
		}

		val estimateDistincts = list.stream().map(c -> c.getFrameNo().v())
													   .distinct().collect(Collectors.toList());
		if(list.size() > estimateDistincts.size()) {
			throw new BusinessException("Msg_1870");
		}

		if(!list.isEmpty() && list.get(0).getFrameNo().v() != 1 ) {
			throw new BusinessException("Msg_1871");
		}

		list.stream().reduce((pre, next) -> {

			if (pre.getFrameNo().v() != (next.getFrameNo().v() - 1)) {
				throw new BusinessException("Msg_1871");
			}

			if (pre.getAmount().v() >= next.getAmount().v()) {
				throw new BusinessException("Msg_147");
			}

			return next;
		});

		return new CriterionAmountList(list);
	}


	/**
	 * 枠NOを指定して目安金額を取得する
	 * @param frameNo 枠NO
	 * @return
	 */
	public Optional<CriterionAmountByNo> getCriterionAmountByNo(CriterionAmountNo frameNo) {
		return this.list.stream()
					.filter( e -> e.getFrameNo().equals( frameNo ) )
					.findFirst();
	}

	/**
	 * 目安金額の段階を判定する
	 * @param require require
	 * @param target 判定対象金額
	 * @return 目安金額の段階
	 */
	public StepOfCriterionAmount getStepOfEstimateAmount(Require require, CriterionAmountValue target) {

		// 枠NO順にソート
		val sortedPrices = this.list.stream()
				.sorted(Comparator.comparing( CriterionAmountByNo::getFrameNo ))
				.collect(Collectors.toList());


		// 未超過金額を取得
		val unexceeded = sortedPrices.stream()
				.filter( e -> e.getAmount().greaterThan( target ) )
				.findFirst();

		if( !unexceeded.isPresent() ) {
			// 未超過金額なし
			return StepOfCriterionAmount.createWithoutUnexceeded( require, sortedPrices.get(sortedPrices.size()-1) );
		} else if( unexceeded.get().getFrameNo().v() == 1 ) {
			// 超過済み金額なし
			return StepOfCriterionAmount.createWithoutExceeded( require, unexceeded.get() );
		}


		// 超過済み金額を取得
		val exceeded = sortedPrices.stream()
				.filter( e -> e.getFrameNo().v() == (unexceeded.get().getFrameNo().v() - 1) )
				.findFirst().get();
		return StepOfCriterionAmount.createFromCondition( require, exceeded, unexceeded.get() );

	}



	public static interface Require
			extends StepOfCriterionAmount.Require {
	}

}
