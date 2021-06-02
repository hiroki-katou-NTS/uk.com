package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

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
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountByNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployeeGettingService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountList;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountValue;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountByNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.StepOfCriterionAmount;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 目安金額に関するHelper
 * @author kumiko_otake
 */
public class CriterionAmountHelper {

	/**
	 * 要件別目安金額を作成する
	 * @param no 枠NO
	 * @param amount 金額
	 * @return 要件別目安金額
	 */
	public static CriterionAmountByNo createAmountPerFrame(int no, int amount) {
		return new CriterionAmountByNo( new CriterionAmountNo( no ), new CriterionAmountValue( amount ) );
	}

	/**
	 * 目安金額リストを作成する
	 * @param amountPerFrame Map<枠NO, 金額>
	 * @return 目安金額リスト
	 */
	public static CriterionAmountList createAmountList(Map<Integer, Integer> amountPerFrame) {
		return CriterionAmountList.create(
					amountPerFrame.entrySet().stream()
						.map( entry -> CriterionAmountHelper.createAmountPerFrame( entry.getKey(), entry.getValue() ) )
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
	public static CriterionAmountUsageSetting createUsageSetting(String cmpId, boolean isUseForEmployment) {
		return new CriterionAmountUsageSetting( cmpId, (isUseForEmployment) ? NotUseAtr.USE : NotUseAtr.NOT_USE );
	}


	/* ↓↓↓目安金額の扱い↓↓↓ **/
	/**
	 * 目安金額の扱いを作成する
	 * @param frameNo 取得したい枠NO
	 * @return 目安金額の扱い
	 */
	public static HandlingOfCriterionAmount createHandling(int...frameNo) {
		return new HandlingOfCriterionAmount(
					IntStream.of( frameNo ).boxed()
						.map( no -> new HandlingOfCriterionAmountByNo( new CriterionAmountNo( no )
											, new ColorCode( "#" + String.join("", Collections.nCopies(6, no.toString()) ) ) )
						).collect(Collectors.toList())
				);
	}


	/* ↓↓↓会社/雇用の目安金額・目安金額↓↓↓ */
	/**
	 * 目安金額を作成する
	 * @param yearly
	 * @param monthly
	 * @return
	 */
	public static CriterionAmount createCriterionAmount(List<Integer> yearly, List<Integer> monthly) {

		return new CriterionAmount(
					CriterionAmountHelper.createAmountList(
						IntStream.rangeClosed( 0, yearly.size()-1 ).boxed()
							.collect(Collectors.toMap( idx -> idx+1, idx -> yearly.get(idx) ))
					)
				,	CriterionAmountHelper.createAmountList(
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
	public static void mockupRequireForStepOfCriterionAmount(StepOfCriterionAmount.Require require, HandlingOfCriterionAmount handling) {

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
	public static <T extends StepOfCriterionAmount.Require> StepOfCriterionAmount createStep(T require, int no, int exceeded, Optional<Integer> unexceeded) {
		return StepOfCriterionAmount.create(require, new CriterionAmountNo(no), new CriterionAmountValue(exceeded), unexceeded.map(CriterionAmountValue::new));
	}


	/* ↓↓↓社員の目安金額を取得する↓↓↓ */
	/**
	 * Mockup設定 『社員の目安金額を取得する#取得する』実行用
	 * @param detail 返したい目安金額詳細
	 */
	public static void mockupGettingService(CriterionAmount detail) {

		new MockUp<CriterionAmountForEmployeeGettingService>() {
			// 社員の目安金額を取得する
			@SuppressWarnings("unused")
			@Mock public CriterionAmount get(CriterionAmountForEmployeeGettingService.Require require, EmployeeId empId, GeneralDate date) {
				return detail;
			}
		};

	}

}
