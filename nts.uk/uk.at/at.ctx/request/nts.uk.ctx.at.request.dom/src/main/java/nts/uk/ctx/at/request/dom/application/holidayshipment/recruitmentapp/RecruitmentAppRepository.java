package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import java.util.Optional;

public interface RecruitmentAppRepository {
	public void insert(RecruitmentApp recApp);

	public Optional<RecruitmentApp> findByID(String applicationID);

	public void update(RecruitmentApp recApp);

	public void remove(String appID);
	/**
	 * find RecruitmentApp By AppId
	 * @author hoatt
	 * @param appId
	 * @return
	 */
	public Optional<RecruitmentApp> findByAppId(String appId);

}
