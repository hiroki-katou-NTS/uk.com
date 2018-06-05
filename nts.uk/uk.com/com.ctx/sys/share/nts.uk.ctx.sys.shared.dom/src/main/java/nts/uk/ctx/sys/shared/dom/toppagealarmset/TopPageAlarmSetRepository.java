package nts.uk.ctx.sys.shared.dom.toppagealarmset;

import java.util.List;
import java.util.Optional;

public interface TopPageAlarmSetRepository {
	List<TopPageAlarmSet> getAll(String companyId);
	void update(TopPageAlarmSet topPageAlarmSet);
}
