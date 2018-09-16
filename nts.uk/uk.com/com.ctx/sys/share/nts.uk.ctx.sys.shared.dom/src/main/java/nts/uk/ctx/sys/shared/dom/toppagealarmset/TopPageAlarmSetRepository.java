package nts.uk.ctx.sys.shared.dom.toppagealarmset;

import java.util.List;
import java.util.Optional;

public interface TopPageAlarmSetRepository {
	List<TopPageAlarmSet> getAll(String companyId);
	Optional<TopPageAlarmSet> getByAlarmCategory(String companyId, int alarmCategory);
	void update(TopPageAlarmSet topPageAlarmSet);
	void delete(String  companyID);
	void create(TopPageAlarmSet topPageAlarmSet);
}
