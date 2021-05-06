package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import java.util.List;
import java.util.Optional;

public interface PersisAlarmListExtractResultRepository {
    Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String runCode, String patternCode, List<String> empIds);

    Optional<PersistenceAlarmListExtractResult> getAlarmExtractResult(String companyId, String patternCode, String runCode);

    void insert(PersistenceAlarmListExtractResult domain);

    void delete(PersistenceAlarmListExtractResult domain);
}
