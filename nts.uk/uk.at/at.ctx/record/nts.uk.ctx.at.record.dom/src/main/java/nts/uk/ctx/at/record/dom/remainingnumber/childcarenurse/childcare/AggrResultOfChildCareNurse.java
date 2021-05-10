package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildcareNurseRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveGrantUseDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUnDigestion;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 子の看護介護休暇集計結果
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class AggrResultOfChildCareNurse {

	/** エラー情報 */
	private List<ChildCareNurseErrors> childCareNurseErrors;
	/** 期間終了日の翌日時点での使用数 */
	private ChildCareNurseUsedNumber asOfPeriodEnd;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateDaysInfo  startdateDays;
	/** 起算日を含む期間フラグ */
	private boolean startDateAtr;
	/** 集計期間の休暇情報*/
	private ChildCareNurseAggrPeriodDaysInfo aggrperiodinfo;

	/**
	 * コンストラクタ
	 */
	public AggrResultOfChildCareNurse(){
		this.childCareNurseErrors = new ArrayList<>();
		this.asOfPeriodEnd = new ChildCareNurseUsedNumber();
		this.startdateDays = new ChildCareNurseStartdateDaysInfo();
		this.startDateAtr = false;
		this.aggrperiodinfo = new ChildCareNurseAggrPeriodDaysInfo();
	}

	/**
	 * ファクトリー
	 * @param childCareNurseErrors エラー情報
	 * @param asOfPeriodEnd 期間終了日の翌日時点での使用数
	 * @param startdateDays  起算日からの休暇情報
	 * @param startDateAtr 起算日を含む期間フラグ
	 * @param aggrperiodinfo  集計期間の休暇情報
	 * @return 子の看護介護休暇集計結果
	 */
	public static AggrResultOfChildCareNurse of (
			List<ChildCareNurseErrors> childCareNurseErrors,
			ChildCareNurseUsedNumber asOfPeriodEnd,
			ChildCareNurseStartdateDaysInfo startdateDays,
			boolean startDateAtr,
			ChildCareNurseAggrPeriodDaysInfo aggrperiodinfo){

		AggrResultOfChildCareNurse domain = new AggrResultOfChildCareNurse();
		domain.childCareNurseErrors = childCareNurseErrors;
		domain.asOfPeriodEnd = asOfPeriodEnd;
		domain.startdateDays = startdateDays;
		domain.startDateAtr = startDateAtr;
		domain.aggrperiodinfo = aggrperiodinfo;
		return domain;
	}

	/**
	 * 子の看護介護-月別残数データ作成
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @param period
	 * @param childCareNurseResult
	 * @return
	 */
	public ChildcareNurseRemNumEachMonth createRemainData(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod period,
			AggrResultOfChildCareNurse childCareNurseResult){

		// 【項目移送】
		ChildcareNurseRemNumEachMonth domain
			= new ChildcareNurseRemNumEachMonth(
					employeeId, yearMonth, closureId, closureDate);

		// 社員ID ← パラメータ「社員ID」
		domain.setEmployeeId(employeeId);

		// 年月 ← パラメータ「年月」
		domain.setYearMonth(yearMonth);

		// 締めID ← パラメータ「締めID」
		domain.setClosureId(closureId);

		// 締め日 ← パラメータ「締め日」
		domain.setClosureDate(closureDate);

		// 締め処理状態 ← ”未締め”
		domain.setClosureStatus(ClosureStatus.UNTREATED); // 未締め

		//	本年使用数←output.集計期間の休暇情報.本年
		domain.setThisYearUsedInfo(
				ChildCareNurseUsedInfo.of(
						childCareNurseResult.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().clone(),
						childCareNurseResult.getAggrperiodinfo().getThisYear().getUsedCount().clone(),
						childCareNurseResult.getAggrperiodinfo().getThisYear().getUsedDays().clone())
						);




//	翌年使用数←output.集計期間の休暇情報.翌年
//	合計使用数←output.集計期間の休暇情報の本年と翌年の合計
//
//	本年残数←output.起算日からの休暇情報.本年.残数
//	翌年残数←output.起算日からの休暇情報.翌年.残数








//		// 特別休暇月別残数データ.特別休暇←特別休暇情報（期間終了日の期間終了日時点）.残数.特別休暇(マイナスなし)
//		domain.setSpecialLeave(childCareNurseResult.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveNoMinus());
//
//		//特別休暇月別残数データ.実特別休暇←特別休暇情報（期間終了日の期間終了日時点）.残数.特別休暇(マイナスあり)
//		domain.setActualSpecial(childCareNurseResult.getAsOfPeriodEnd().getRemainingNumber().getSpecialLeaveWithMinus());
//
//		//特別休暇月別残数データ.未消化数　←　特別休暇情報（期間終了日の翌日開始時点）．残数．未消化数
//		domain.setUnDegestionNumber(
//				childCareNurseResult.getAsOfStartNextDayOfPeriodEnd().getRemainingNumber().getSpecialLeaveUndigestNumber()
//						.map(x -> new SpecialLeaveUnDigestion(new SpecialLeaveRemainDay(x.getDays().v()),
//								x.getMinutes().map(y -> new SpecialLeavaRemainTime(y.v()))))
//						.orElse(new SpecialLeaveUnDigestion(new SpecialLeaveRemainDay(0.0) , Optional.empty())));
//
//		if(childCareNurseResult.getAsOfStartNextDayOfPeriodEnd().getRemainingNumber()
//				.getSpecialLeaveWithMinus().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()) {
//			// 付与区分 ← （特別休暇情報（期間終了日の翌日開始時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在する場合）true
//			domain.setGrantAtr(true);
//			// 特別休暇情報（期間終了日の翌日開始時点）．付与残数データ．付与日がINPUT．期間．開始＋１日～INPUT．期間．終了日＋１の付与残数データ．明細．付与数．日数
//			DatePeriod periodTemp = new DatePeriod(period.start().addDays(1), period.end().addDays(1));
//			double grantUseDay = childCareNurseResult.getAsOfStartNextDayOfPeriodEnd().getGrantRemainingDataList().stream()
//					.filter(x -> x.getGrantDate().beforeOrEquals(periodTemp.end())
//							&& x.getGrantDate().afterOrEquals(periodTemp.start())).mapToDouble(x -> x.getDetails().getGrantNumber().getDays().v()).sum();
//			domain.setGrantDays(Optional.of(new SpecialLeaveGrantUseDay(grantUseDay)));
//		}else {
//			//付与区分 ← （特別休暇情報（期間終了日の翌日開始時点）．残数．特別休暇（マイナスあり）残数．付与後）が存在しない場合）false
//			domain.setGrantAtr(false);
//			//存在しない場合 optional.empty
//			domain.setGrantDays(Optional.empty());
//		}

		return domain;
	}

}