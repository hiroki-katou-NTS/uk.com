package nts.uk.ctx.bs.person.pub.anniversary;

import java.util.Map;
import nts.arc.time.calendar.period.DatePeriod;

public interface AnniversaryNoticePub {
	
	/**
	 * 期間で記念日情報を取得する
	 * @param datePeriod
	 * @return Map<AnniversaryNoticeExport, Boolean>
	 */
	public Map<AnniversaryNoticeExport, Boolean> setFlag(DatePeriod datePeriod);
}
