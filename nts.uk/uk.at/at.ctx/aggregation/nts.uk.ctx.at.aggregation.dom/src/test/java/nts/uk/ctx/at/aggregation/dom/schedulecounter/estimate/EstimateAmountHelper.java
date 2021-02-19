package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 目安金額に関するHelper
 * @author kumiko_otake
 */
public class EstimateAmountHelper {

	/**
	 * 要件別目安金額を作成する
	 * @param no 枠NO
	 * @param amount 金額
	 * @return 要件別目安金額
	 */
	public static EstimateAmountByCondition createAmountPerFrame(int no, int amount) {
		return new EstimateAmountByCondition( new EstimateAmountNo( no ), new EstimateAmount( amount ) );
	}


	/* ↓↓↓目安利用区分↓↓↓ **/
	/**
	 * 目安利用区分を作成する
	 * @param cmpId 会社ID
	 * @param isUseForEmployment 雇用別を使用するか
	 * @return 目安利用区分
	 */
	public static EstimateAmountUsageSetting createUsageSetting(String cmpId, boolean isUseForEmployment) {
		return new EstimateAmountUsageSetting( cmpId, (isUseForEmployment) ? NotUseAtr.USE : NotUseAtr.NOT_USE );
	}


	/* ↓↓↓目安金額の扱い↓↓↓ **/
	/**
	 * 目安金額の扱いを作成する
	 * @param frameNo 取得したい枠NO
	 * @return 目安金額の扱い
	 */
	public static HandingOfEstimateAmount createHandling(int...frameNo) {
		return new HandingOfEstimateAmount(
					IntStream.of( frameNo ).boxed()
						.map( no -> new HandleFrameNo( new EstimateAmountNo( no )
											, new ColorCode( "#" + String.join("", Collections.nCopies(6, no.toString()) ) ) )
						).collect(Collectors.toList())
				);
	}


	/* ↓↓↓目安金額の段階↓↓↓ **/
	/**
	 * 目安金額の段階を作成する
	 * @param require require
	 * @param no 枠NO
	 * @param exceeded 超過済み金額
	 * @param unexceeded 未超過金額
	 * @return 目安金額の段階
	 */
	public static <T extends StepOfEstimateAmount.Require> StepOfEstimateAmount createStep(T require, int no, int exceeded, Optional<Integer> unexceeded) {
		return StepOfEstimateAmount.create(require, new EstimateAmountNo(no), new EstimateAmount(exceeded), unexceeded.map(EstimateAmount::new));
	}

}
