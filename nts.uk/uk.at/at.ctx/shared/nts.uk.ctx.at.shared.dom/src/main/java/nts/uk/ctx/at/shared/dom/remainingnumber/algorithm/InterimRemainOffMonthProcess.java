package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimRemainOffMonthProcess {
	/**
	 * 月次処理用の暫定残数管理データを作成する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param dateData 期間
	 * @return
	 */
	public Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(String cid, String sid, DatePeriod dateData);
	
}
