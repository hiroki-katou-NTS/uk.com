package nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng;

import java.util.Optional;

public interface CompltLeaveSimMngRepository {
	public void insert(CompltLeaveSimMng domain);

	public Optional<CompltLeaveSimMng> findByRecID(String appID);
}
