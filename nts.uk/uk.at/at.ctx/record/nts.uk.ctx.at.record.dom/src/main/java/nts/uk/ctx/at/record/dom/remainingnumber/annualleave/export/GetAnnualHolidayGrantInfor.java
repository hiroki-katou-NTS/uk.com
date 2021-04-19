package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrant;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.DailyInterimRemainMngDataAndFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface GetAnnualHolidayGrantInfor {
	/**
	 * [NO.550]年休付与情報を取得
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param referenceAtr 参照先区分
	 * @param ym 指定年月
	 * @param ymd 基準日 - reference date
	 * @param periodOutput 対象期間区分（現在/１年経過時点/過去）
	 * @param fromTo １年経過用期間(From-To)
	 * @param isDoubletrack
	 * @param exCondition
	 * @param exConditionDays
	 * @param exComparison
	 * @return 年休付与情報 - GetAnnualHolidayGrantInforDto ( 年休付与情報  and 抽出対象社員 )
	 */
	GetAnnualHolidayGrantInforDto getAnnGrantInfor(String cid, String sid, ReferenceAtr referenceAtr, YearMonth ym,
			GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo, boolean doubletrack , 
			boolean exCondition,int exConditionDays, int exComparison);
	/**
	 * [NO.551]期間内の年休使用明細を取得する
	 * @param cid
	 * @param sid
	 * @param datePeriod
	 * @param referenceAtr
	 * @return
	 */
	List<DailyInterimRemainMngDataAndFlg> lstRemainData(String cid, String sid, DatePeriod datePeriod, ReferenceAtr referenceAtr);
	/**
	 * 指定月時点の付与数、使用数を計算
	 * @param sid 社員ID
	 * @param lstAnnRemainHis 年休付与残数履歴データ(List)
	 * @param ymd 指定年月日
	 * @return 年休付与残数履歴データ(List)
	 * 
	 */
	List<AnnualLeaveGrantRemainingData> lstRemainHistory(String sid, List<AnnualLeaveGrantRemainingData> lstAnnRemainHis, GeneralDate ymd);
	/**
	 * 前回付与日から指定年月の間で期限が切れた付与情報を取得
	 * @param sid 社員ID
	 * @param ym 指定年月
	 * @param closureID 締めID
	 * @param closureDate 締め日
	 * @param period 指定期間(型：期間)
	 * @param isPastMonth 過去月集計モー
	 * @return
	 */
	List<AnnualHolidayGrant> grantInforFormPeriod(String sid, YearMonth ym, ClosureId closureID, ClosureDate closureDate, 
			DatePeriod period, boolean isPastMonth);
	
}
