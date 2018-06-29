package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimSpecialHolidayMngRepository {
	/**
	 * ドメインモデル「特別休暇暫定データ」を取得する
	 * @param sId
	 * @param dateData ・INPUT．集計年月日<=年月日<=INPUT．集計終了日
	 * @return
	 */
	List<InterimSpecialHolidayMng> findBySidPeriod(String sId, DatePeriod dateData);
	/**
	 * 更新か追加
	 * @param domain
	 */
	void persistAndUpdateInterimSpecialHoliday(InterimSpecialHolidayMng domain);
	/**
	 * 削除 by ID
	 * @param specialId
	 */
	void deleteSpecialHoliday(String specialId);
}
