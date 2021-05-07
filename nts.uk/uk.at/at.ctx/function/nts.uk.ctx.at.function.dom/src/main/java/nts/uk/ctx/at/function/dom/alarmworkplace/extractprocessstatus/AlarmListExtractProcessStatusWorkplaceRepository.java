package nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus;

import java.util.List;
import java.util.Optional;

public interface AlarmListExtractProcessStatusWorkplaceRepository {
    Optional<AlarmListExtractProcessStatusWorkplace> getBy(String companyId, String id);

    List<AlarmListExtractProcessStatusWorkplace> getBy(String companyId, String employeeId, ExtractState status);

    void add(AlarmListExtractProcessStatusWorkplace processStatus);

    void update(AlarmListExtractProcessStatusWorkplace processStatus);
}
