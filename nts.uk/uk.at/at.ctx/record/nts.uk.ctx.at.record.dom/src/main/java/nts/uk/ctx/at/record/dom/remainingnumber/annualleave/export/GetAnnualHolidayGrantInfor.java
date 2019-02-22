package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.DailyInterimRemainMngDataAndFlg;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface GetAnnualHolidayGrantInfor {
	/**
	 * [NO.550]年休付与情報を取得
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param referenceAtr 参照先区分
	 * @param ym 指定年月
	 * @param ymd 基準日
	 * @return 年休付与情報
	 */
	Optional<AnnualHolidayGrantInfor> getAnnGrantInfor(String cid, String sid, ReferenceAtr referenceAtr, YearMonth ym,
			GeneralDate ymd);
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
	 * @return 年休付与残数履歴データ(List)
	 */
	List<AnnualLeaveGrantRemainingData> lstRemainHistory(String sid, List<AnnualLeaveGrantRemainingData> lstAnnRemainHis);
	
}
