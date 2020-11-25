package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

import java.util.List;

public interface AlarmFixedExtractionConditionRepository {

    List<AlarmFixedExtractionCondition> getByID(String id);

    List<AlarmFixedExtractionCondition> getByIDs(List<String> ids);

    List<AlarmFixedExtractionCondition> getBy(List<String> ids, boolean useAtr);

}
