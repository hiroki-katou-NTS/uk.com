package nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim;

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
}
