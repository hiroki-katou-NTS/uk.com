package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;

import java.util.Optional;
public interface InterimBreakDayOffMngRepository {
	/**
	 * 暫定休出管理データ
	 * @param breakManaId
	 * @return
	 */
	Optional<InterimBreakMng> getBreakManaBybreakMngId(String breakManaId);
	/**
	 * 暫定代休管理データ
	 * @param dayOffManaId
	 * @return
	 */
	Optional<InterimDayOffMng> getDayoffById(String dayOffManaId);
	/**
	 * 暫定休出代休紐付け管理
	 * @param mngId
	 * @param breakDay: True: 休出管理データ, false: 代休管理データ
	 * @return
	 */
	Optional<InterimBreakDayOffMng> getBreakDayOffMng(String mngId, boolean breakDay);
}
