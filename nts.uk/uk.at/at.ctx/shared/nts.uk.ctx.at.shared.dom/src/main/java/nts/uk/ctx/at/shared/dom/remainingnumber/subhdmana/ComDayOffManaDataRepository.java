package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ComDayOffManaDataRepository {

	/**
	 *  ドメインモデル「代休管理データ」を取得
	 *  未相殺日数>0.0
	 * @param cid
	 * @param sid
	 * @return
	 */
	List<CompensatoryDayOffManaData> getBySidWithReDay(String cid, String sid);
	
	List<CompensatoryDayOffManaData> getByReDay(String cid, String sid);

	List<CompensatoryDayOffManaData> getBySidComDayOffIdWithReDay(String cid, String sid, String leaveId);
	
	List<CompensatoryDayOffManaData> getByHoliday(String sid, Boolean unknownDate, DatePeriod dayOff);

	List<CompensatoryDayOffManaData> getBySid(String cid, String sid);

	/**
	 * 
	 * @param cid
	 * @param sid
	 * @param ymd ・代休日 < INPUT．集計開始日
	 * @return
	 */
	List<CompensatoryDayOffManaData> getBySidDate(String cid, String sid, GeneralDate ymd);

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
	
	void updateReDayReqByComDayId(String comDayId,Boolean check);
	
	Optional<CompensatoryDayOffManaData> getBycomdayOffId(String comDayOffId);
	
	void updateRemainDay(String comDayOffID, Double remainDay);

	/**
	 * ドメインモデル「休出管理データ」を取得する
	 * @param sid
	 * @param dateData ・休出日が指定期間内
	 * ・休出日≧INPUT.期間.開始年月日
	 * ・休出日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<CompensatoryDayOffManaData> getByDayOffDatePeriod(String sid, DatePeriod dateData);
	
	void deleteById(List<String> comDayOffID);
}
