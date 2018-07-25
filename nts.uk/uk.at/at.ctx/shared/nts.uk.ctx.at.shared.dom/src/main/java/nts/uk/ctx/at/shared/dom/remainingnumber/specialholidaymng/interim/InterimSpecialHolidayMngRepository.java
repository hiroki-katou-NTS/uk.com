package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.List;

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
}
