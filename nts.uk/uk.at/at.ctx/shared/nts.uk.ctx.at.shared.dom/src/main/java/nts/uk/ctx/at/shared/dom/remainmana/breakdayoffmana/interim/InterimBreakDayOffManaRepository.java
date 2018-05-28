package nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.interim;

import java.util.Optional;
public interface InterimBreakDayOffManaRepository {
	/**
	 * 暫定休出管理データ
	 * @param breakManaId
	 * @return
	 */
	Optional<InterimBreakMana> getBreakManaBybreakManaId(String breakManaId);
	/**
	 * 暫定代休管理データ
	 * @param dayOffManaId
	 * @return
	 */
	Optional<InterimDayOffMana> getDayoffById(String dayOffManaId);
}
