package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ComDayOffManaDataRepository {
	/**
	 * ドメインモデル「代休管理データ」を取得
	 * 
	 * @param sid
	 * @return
	 */
	// 社員ID=パラメータ「社員ID」
	// 未相殺日数>0.0
	List<CompensatoryDayOffManaData> getBySidWithReDay(String cid, String sid);
	
	List<CompensatoryDayOffManaData> getByReDay(String cid, String sid);

	List<CompensatoryDayOffManaData> getBySidComDayOffIdWithReDay(String cid, String sid, String leaveId);
	


	List<CompensatoryDayOffManaData> getBySid(String cid, String sid);

	List<CompensatoryDayOffManaData> getByDateCondition(String cid, String sid, GeneralDate startDate,
			GeneralDate endDate);

	List<CompensatoryDayOffManaData> getBySidWithHolidayDateCondition(String cid, String sid,
			GeneralDate dateSubHoliday);

	void create(CompensatoryDayOffManaData domain);

	/**
	 * Update domain 代休管理データ
	 * @param domain
	 */
	void update(CompensatoryDayOffManaData domain);

	/**
	 * Delete domain 代休管理データ
	 * @param comDayOffID ID
	 */
	void deleteByComDayOffId(String comDayOffID);
	
	void updateReDayByComDayId(List<String> comDayIds);
	
	void updateReDayReqByComDayId(List<String> comDayIds);
}
