package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
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

	/**
	 * 目安金額リストを作成する
	 * @param amountPerFrame Map<枠NO, 金額>
	 * @return 目安金額リスト
	 */
	public static EstimateAmountList createAmountList(Map<Integer, Integer> amountPerFrame) {
		return EstimateAmountList.create(
					amountPerFrame.entrySet().stream()
						.map( entry -> EstimateAmountHelper.createAmountPerFrame( entry.getKey(), entry.getValue() ) )
						.collect(Collectors.toList())
				);
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


	/* ↓↓↓会社/雇用の目安金額・目安金額詳細↓↓↓ */
	/**
	 * 目安金額詳細を作成する
	 * @param yearly
	 * @param monthly
	 * @return
	 */
	public static EstimateAmountDetail createEstimateDetail(List<Integer> yearly, List<Integer> monthly) {

		return new EstimateAmountDetail(
					EstimateAmountHelper.createAmountList(
						IntStream.rangeClosed( 0, yearly.size()-1 ).boxed()
							.collect(Collectors.toMap( idx -> idx+1, idx -> yearly.get(idx) ))
					)
				,	EstimateAmountHelper.createAmountList(
						IntStream.rangeClosed( 0, monthly.size()-1 ).boxed()
							.collect(Collectors.toMap( idx -> idx+1, idx -> monthly.get(idx) ))
					)
				);

	}


	/* ↓↓↓目安金額の段階↓↓↓ **/
	/**
	 * Mockup設定 『目安金額の段階』作成用
	 * @param require require
	 * @param handling 目安金額の扱い
	 */
	public static void mockupRequireForStepOfEstimateAmount(StepOfEstimateAmount.Require require, HandingOfEstimateAmount handling) {

		// 目安金額関連 Mockup設定
		new Expectations() {{
			// 目安金額の扱いを取得する
			require.getHandling();
			result = Optional.of(handling);
		}};

	}

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


	/* ↓↓↓社員の目安金額を取得する↓↓↓ */
	/**
	 * Mockup設定 『社員の目安金額を取得する#取得する』実行用
	 * @param detail 返したい目安金額詳細
	 */
	public static void mockupGettingService(EstimateAmountDetail detail) {

		new MockUp<EstimateAmountForEmployeeGettingService>() {
			// 社員の目安金額を取得する
			@SuppressWarnings("unused")
			@Mock public EstimateAmountDetail get(EstimateAmountForEmployeeGettingService.Require require, EmployeeId empId, GeneralDate date) {
				return detail;
			}
		};

	}

}
