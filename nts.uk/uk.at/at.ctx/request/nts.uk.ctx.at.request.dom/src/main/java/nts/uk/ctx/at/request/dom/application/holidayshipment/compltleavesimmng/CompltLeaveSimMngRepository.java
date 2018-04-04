package nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng;

import java.util.Optional;

public interface CompltLeaveSimMngRepository {
	public void insert(CompltLeaveSimMng domain);

	public Optional<CompltLeaveSimMng> findByRecID(String appID);

	public Optional<CompltLeaveSimMng> findByAbsID(String applicationID);

	public void remove(String absAppID, String recAppID);

	public void update(CompltLeaveSimMng compltLeaveSimMng);
	/**
	 * find CompltLeaveSimMng By AppId
	 * @author hoatt
	 * @param appId
	 * @return
	 */
	public Optional<CompltLeaveSimMng> findByAppId(String appId);
}
