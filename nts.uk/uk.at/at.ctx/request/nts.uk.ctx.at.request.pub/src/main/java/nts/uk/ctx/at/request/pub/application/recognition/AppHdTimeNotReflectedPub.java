package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AppHdTimeNotReflectedPub {
	/**
	 * 社員、期間から未反映の申請休出時間を集計する
	 * RequestList299
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ApplicationHdTimeExport> acquireTotalAppHdTimeNotReflected(String sId, GeneralDate startDate, GeneralDate endDate);
}
