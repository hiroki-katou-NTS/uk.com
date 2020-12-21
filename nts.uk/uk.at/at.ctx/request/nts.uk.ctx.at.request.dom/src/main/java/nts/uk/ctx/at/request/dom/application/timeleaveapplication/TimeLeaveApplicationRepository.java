package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import java.util.Optional;

public interface TimeLeaveApplicationRepository {

    Optional<TimeLeaveApplication> findByKeys(String companyId, String appId, int appTimeType,int frameNo);

    void add(TimeLeaveApplication domain);

    void update(TimeLeaveApplication domain);

    void remove(TimeLeaveApplication domain);

}
