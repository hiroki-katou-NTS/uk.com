package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface InterimRemainOffMonthProcess {
	/**
	 * 月次処理用の暫定残数管理データを作成する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param dateData 期間
	 * @param yearMonth 年月
	 * @return
	 */
	public FixedRemainDataForMonthlyAgg monthInterimRemainData(CacheCarrier cacheCarrier, String cid, String sid, 
			DatePeriod dateData, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
}
