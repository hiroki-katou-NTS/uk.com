package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import java.util.Optional;

public interface TimeLeaveApplicationRepository {

	Optional<TimeLeaveApplication> findById(String companyId, String appId);

	void add(TimeLeaveApplication domain);

	void update(TimeLeaveApplication domain);

	void remove(TimeLeaveApplication domain);

}
