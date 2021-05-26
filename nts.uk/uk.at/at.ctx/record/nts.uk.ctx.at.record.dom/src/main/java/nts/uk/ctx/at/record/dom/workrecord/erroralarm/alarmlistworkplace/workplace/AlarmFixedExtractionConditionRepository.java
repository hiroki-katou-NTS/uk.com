package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

import java.util.List;

public interface AlarmFixedExtractionConditionRepository {

    List<AlarmFixedExtractionCondition> getByIDs(List<String> ids);

    List<AlarmFixedExtractionCondition> getBy(List<String> ids, boolean useAtr);

    void register(List<AlarmFixedExtractionCondition> domain);

    void delete(List<String> ids);

}
