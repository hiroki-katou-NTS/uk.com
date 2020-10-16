package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.service.TempChildCareNurseManagement;

/**
 * 子の看護介護休暇 集計期間WORK
  * @author yuri_tamakoshi
 */
@Getter
public class AggregateChildCareNurseWork {
	/** 期間 */
	private DatePeriod period;
	/** 暫定子の看護介護管理データ */
	private TempChildCareNurseManagement provisionalDate;
	/** 期間終了後翌日 */
	private NextDayAfterPeriodEndWork nextDayAfterPeriodEnd;
	/** 本年翌年の期間区分 */
	private YearAtr yearAtr;
	/** 集計結果（finally） */
	private Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse;

	/**
	 * コンストラクタ
	 */
	public AggregateChildCareNurseWork(){

		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.provisionalDate = new TempChildCareNurseManagement();
		this.nextDayAfterPeriodEnd = new NextDayAfterPeriodEndWork();
		this.yearAtr = YearAtr.THIS_YEAR; //一時対応
		this.aggrResultOfChildCareNurse = Finally.empty();
	}

	/**
	 * ファクトリー
	 * @param period 期間
	 * @param provisionalDate 暫定子の看護介護管理データ
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param YearAtr 本年翌年の期間区分
	 * @param AggrResultOfChildCareNurse 集計結果（finally）
	 * @return 子の看護介護休暇 集計期間
	 */
	public static AggregateChildCareNurseWork of(
		DatePeriod period,
		TempChildCareNurseManagement provisionalDate,
		NextDayAfterPeriodEndWork nextDayAfterPeriodEnd,
		YearAtr yearAtr,
		Finally<ChildCareNurseCalcResultWithinPeriod> aggrResultOfChildCareNurse){

	AggregateChildCareNurseWork domain = new AggregateChildCareNurseWork();
	domain.period = period;
	domain.provisionalDate = provisionalDate;
	domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
	domain.yearAtr = yearAtr;
	domain.aggrResultOfChildCareNurse = aggrResultOfChildCareNurse;
	return domain;
	}

	public boolean isThisYear() {
		return this.yearAtr == YearAtr.THIS_YEAR;
	}

	public boolean isNextYear() {
		return this.yearAtr == YearAtr.NEXT_YEAR;
	}
}