package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import java.util.List;
import java.util.Optional;

public interface AlarmListExtractInfoWorkplaceRepository {

    void addAll(List<AlarmListExtractInfoWorkplace> domains);
    Optional<AlarmListExtractInfoWorkplace> getById(String processingId);
}
