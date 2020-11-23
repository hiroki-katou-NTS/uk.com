package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition;

import java.util.List;

public interface ExtractionCondition {
    // Get AlarmCheckWorkplace ID (Fixed item)
    List<String> getAlarmCheckWkpID();

    // Get Optional Item Ids
    List<String> getListOptionalIDs();
}
