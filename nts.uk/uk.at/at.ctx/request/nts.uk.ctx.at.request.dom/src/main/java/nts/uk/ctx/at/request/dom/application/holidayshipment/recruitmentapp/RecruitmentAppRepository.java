package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import java.util.Optional;

public interface RecruitmentAppRepository {
	public void insert(RecruitmentApp recApp);

	public Optional<RecruitmentApp> findByID(String applicationID);

	public void update(RecruitmentApp recApp);

	public void remove(String appID);
}
