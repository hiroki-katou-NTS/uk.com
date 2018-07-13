package nts.uk.ctx.sys.shared.dom.toppagealarmset;

import java.util.List;

public interface TopPageAlarmSetRepository {
	List<TopPageAlarmSet> getAll(String companyId);
	void update(TopPageAlarmSet topPageAlarmSet);
	void delete(String  companyID);
	void create(TopPageAlarmSet topPageAlarmSet);
}
