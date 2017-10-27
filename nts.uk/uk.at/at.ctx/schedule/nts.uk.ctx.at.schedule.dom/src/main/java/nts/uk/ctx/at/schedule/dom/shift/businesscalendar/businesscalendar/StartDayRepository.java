package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar;

import java.util.Optional;

public interface StartDayRepository {
	/**
	 * get StartDay by Company ID
	 * @param companyId
	 * @return
	 */
	Optional<StartDayItem> findByCompany(String companyId);

}
