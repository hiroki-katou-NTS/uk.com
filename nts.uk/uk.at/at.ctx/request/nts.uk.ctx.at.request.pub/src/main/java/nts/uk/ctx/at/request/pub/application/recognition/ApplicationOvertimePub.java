package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApplicationOvertimePub {
	/**
	 * 社員、期間から未反映の申請残業時間を集計する
	 * RequestList236
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ApplicationOvertimeExport> acquireTotalApplicationOverTimeHours(String sId, GeneralDate startDate, GeneralDate endDate);
}
