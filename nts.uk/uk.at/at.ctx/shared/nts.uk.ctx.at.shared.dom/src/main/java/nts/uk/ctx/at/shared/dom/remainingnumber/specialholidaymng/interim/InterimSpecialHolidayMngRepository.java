package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface InterimSpecialHolidayMngRepository {
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
	/**
	 * 検索
	 * @param mngId　特休ID
	 * @return
	 */
	List<InterimSpecialHolidayMng> findById(String mngId);
	
	/**
	 * 削除 by ID
	 * @param specialId
	 */
	void deleteSpecialHolidayBySidAndYmd(String sId , GeneralDate ymd);
	
	/**
	 * 検索
	 * @param mngId　特休ID
	 * @return
	 */
	List<InterimSpecialHolidayMng> findSpecialHolidayBySidAndPeriod(String sId , DatePeriod period);
	
	/**
	 * 削除　期間
	 * @param sId
	 * @param specialCd
	 * @param period
	 */
	void deleteBySidAndPeriod(String sid ,int specialCd,  DatePeriod period);
}
