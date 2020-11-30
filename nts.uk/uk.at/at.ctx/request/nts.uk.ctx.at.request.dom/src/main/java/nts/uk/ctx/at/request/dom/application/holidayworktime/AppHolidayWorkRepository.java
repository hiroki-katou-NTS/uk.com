package nts.uk.ctx.at.request.dom.application.holidayworktime;

import java.util.Optional;

/**
 * Refactor5
 * @author huylq
 *
 */
public interface AppHolidayWorkRepository {
	
	public Optional<AppHolidayWork> find(String companyId, String applicationId);

	public void add(AppHolidayWork appHolidayWork);
}
