package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.information.childnursing.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildcareNurseRemNumEachMonth;
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
	public ChildcareRemNumEachMonth createRemainData(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod period){

//
//		// 社員ID ← パラメータ「社員ID」
//		domain.setEmployeeId(employeeId);
//
//		// 年月 ← パラメータ「年月」
//		domain.setYearMonth(yearMonth);
//
//		// 締めID ← パラメータ「締めID」
//		domain.setClosureId(closureId);
//
//		// 締め日 ← パラメータ「締め日」
//		domain.setClosureDate(closureDate);
//
//		// 締め処理状態 ← ”未締め”
//		domain.setClosureStatus(ClosureStatus.UNTREATED); // 未締め


		/** 本年使用数 */
		ChildCareNurseUsedInfo thisYearUsedInfo = this.aggrperiodinfo.getThisYear().clone();
		/** 本年残数 */
		ChildCareNurseRemainingNumber thisYearRemainNumber = this.startdateDays.getThisYear().getRemainingNumber().clone();
		/** 翌年使用数 */
		Optional<ChildCareNurseUsedInfo> nextYearUsedInfo= this.aggrperiodinfo.getNextYear().map(c->c.clone());
		/** 翌年残数 */
		Optional<ChildCareNurseRemainingNumber> nextYearRemainNumber=this.startdateDays.getNextYear().map(c->c.getRemainingNumber().clone());
		/** 合計使用数 */
		ChildCareNurseUsedInfo usedInfo = this.aggrperiodinfo.getThisYear().clone();
		if(this.aggrperiodinfo.getNextYear().isPresent()) {
			usedInfo.add(this.aggrperiodinfo.getNextYear().get());
		}

		ChildcareNurseRemNumEachMonth rem = ChildcareNurseRemNumEachMonth.of(
				thisYearUsedInfo,
				usedInfo,
				thisYearRemainNumber,
				nextYearUsedInfo,
				nextYearRemainNumber
				);

		ChildcareRemNumEachMonth domain
		= new ChildcareRemNumEachMonth(
				employeeId, yearMonth, closureId, closureDate, rem);

		return domain;
	}

}