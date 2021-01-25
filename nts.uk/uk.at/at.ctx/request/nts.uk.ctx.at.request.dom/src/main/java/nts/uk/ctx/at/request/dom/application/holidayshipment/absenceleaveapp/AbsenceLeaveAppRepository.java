package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import java.util.Optional;

public interface AbsenceLeaveAppRepository {
	
	public void insert(AbsenceLeaveApp domain);

	public Optional<AbsenceLeaveApp> findByID(String applicationID);

	public void update(AbsenceLeaveApp absApp);

	public void remove(String appID);
	/**
	 * find AbsenceLeaveApp By AppId
	 * @author hoatt
	 * @param applicationID
	 * @return
	 */
	public Optional<AbsenceLeaveApp> findByAppId(String applicationID);
}
