package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import java.util.Optional;

public interface TimeLeaveAppReflectRepository {
    Optional<TimeLeaveApplicationReflect> findByCompany(String companyId);
    void save(TimeLeaveApplicationReflect domain);
}
