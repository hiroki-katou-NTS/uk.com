package nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng;

import java.util.Optional;

public interface AppHdsubRecRepository {
	public void insert(AppHdsubRec domain);

	public Optional<AppHdsubRec> findByRecID(String appID);

	public Optional<AppHdsubRec> findByAbsID(String applicationID);

	public void remove(String absAppID, String recAppID);

	public void update(AppHdsubRec compltLeaveSimMng);
	/**
	 * find CompltLeaveSimMng By AppId
	 * @author hoatt
	 * @param appId
	 * @return
	 */
	public Optional<AppHdsubRec> findByAppId(String appId);
}
