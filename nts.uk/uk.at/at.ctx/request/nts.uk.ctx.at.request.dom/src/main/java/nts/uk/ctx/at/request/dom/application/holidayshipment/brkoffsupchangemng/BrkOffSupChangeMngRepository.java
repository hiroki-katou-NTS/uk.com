package nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng;

import java.util.Optional;

public interface BrkOffSupChangeMngRepository {
	// ドメインモデル「振休申請休出変更管理」に
	void insert(BrkOffSupChangeMng brkOffSupChangeMng);
	
	/**
	 * @param holidayWorkAppID
	 * @return
	 */
	Optional<BrkOffSupChangeMng> findHolidayAppID(String holidayWorkAppID);
	
	void remove(String leaveAppID, String absenceLeaveAppID);
}
