package nts.uk.ctx.at.request.dom.application.holidayworktime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Refactor5
 * @author huylq
 *
 */
public interface AppHolidayWorkRepository {
	
	Optional<AppHolidayWork> find(String companyId, String applicationId);

	void add(AppHolidayWork appHolidayWork);

	void update(AppHolidayWork appHolidayWork);

	void delete(String companyId, String applicationId);

	Map<String, AppHolidayWork> getListAppHdWorkFrame(String companyId, List<String> lstAppId);

}
