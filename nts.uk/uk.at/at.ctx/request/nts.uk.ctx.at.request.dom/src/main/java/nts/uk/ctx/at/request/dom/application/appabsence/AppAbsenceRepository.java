package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.Optional;

public interface AppAbsenceRepository {

	/**
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public Optional<AppAbsence> getAbsenceById(String companyID, String appId);
	
	/**
	 * insertAbsence
	 * @param appAbsence
	 */
	public void insertAbsence(AppAbsence appAbsence);
	
	/**
	 * get Application and AppAbsence
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public Optional<AppAbsence> getAbsenceByAppId(String companyID, String appId);
}
