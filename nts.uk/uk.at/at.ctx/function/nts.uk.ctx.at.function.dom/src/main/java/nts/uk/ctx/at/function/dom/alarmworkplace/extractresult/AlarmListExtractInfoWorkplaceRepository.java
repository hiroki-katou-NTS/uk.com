package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import java.util.List;
import java.util.Optional;

public interface AlarmListExtractInfoWorkplaceRepository {

    void addAll(List<AlarmListExtractInfoWorkplace> domains);
    List<AlarmListExtractInfoWorkplace> getById(String processId);
}
