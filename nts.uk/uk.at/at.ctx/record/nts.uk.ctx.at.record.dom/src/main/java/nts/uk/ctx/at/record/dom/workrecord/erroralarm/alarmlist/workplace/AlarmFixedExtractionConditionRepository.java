package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace;

import java.util.List;
import java.util.Optional;

public interface AlarmFixedExtractionConditionRepository {

    List<AlarmFixedExtractionCondition> getByID(String id);

}
