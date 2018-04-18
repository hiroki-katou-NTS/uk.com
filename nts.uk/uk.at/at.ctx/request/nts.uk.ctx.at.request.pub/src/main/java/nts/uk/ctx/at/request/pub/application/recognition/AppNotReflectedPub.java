package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AppNotReflectedPub {
	/**
	 * 社員、期間から未反映の申請就業時間外深夜時間を集計する
	 * RequestList300
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ApplicationOvertimeExport> acquireAppNotReflected(String sId, GeneralDate startDate, GeneralDate endDate);
}
