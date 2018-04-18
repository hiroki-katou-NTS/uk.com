package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApplicationTimeUnreflectedPub {
	/**
	 * 社員、期間から未反映の申請フレックス超過時間を集計する
	 * RequestList298
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ApplicationOvertimeExport> acquireTotalApplicationTimeUnreflected(String sId, GeneralDate startDate, GeneralDate endDate);
}
