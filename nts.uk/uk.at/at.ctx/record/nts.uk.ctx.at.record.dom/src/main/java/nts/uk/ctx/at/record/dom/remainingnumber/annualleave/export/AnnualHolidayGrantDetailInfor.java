package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;

public interface AnnualHolidayGrantDetailInfor {
	/**
	 * 年休明細情報を取得
	 * @param sid 社員ID
	 * @param referenceAtr 参照先区分 - （実績 or 予定・申請を含む）
	 * @param ym 指定年月 - （対象期間区分が１年経過時点の場合、NULL） - Designated date
	 * @param ymd 基準日- Reference date
	 * @param 対象期間区分（現在/１年経過時点/過去） - Target period classification (current / one year passed / past)
	 * @param １年経過用期間(From-To) - 1 year elapsed period (From-To)
	 * @param  ダブルトラック開始日- Double track start date
	 * @return 年休使用詳細（List) - Annual holiday use details (List)
	 */
	List<AnnualHolidayGrantDetail> getAnnHolidayDetail(String cid, String sid, ReferenceAtr referenceAtr,
			YearMonth ym, GeneralDate ymd, Integer targetPeriod, Optional<DatePeriod> fromTo, Optional<GeneralDate> doubleTrackStartDate);
}
