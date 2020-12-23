package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import java.util.List;

public interface FixedExtractionScheduleItemsRepository {

    List<FixedExtractionScheduleItems> getBy(List<FixedCheckDayItemName> nos);

    List<FixedExtractionScheduleItems> getAll();
}
