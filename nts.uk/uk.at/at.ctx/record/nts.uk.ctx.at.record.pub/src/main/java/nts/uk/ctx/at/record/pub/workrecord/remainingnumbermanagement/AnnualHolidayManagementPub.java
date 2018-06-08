package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import java.util.List;

public interface AnnualHolidayManagementPub {
	/**
	 * RequestList210
	 * 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	List<NextAnnualLeaveGrantExport> acquireNextHolidayGrantDate(String cId, String sId);
}
