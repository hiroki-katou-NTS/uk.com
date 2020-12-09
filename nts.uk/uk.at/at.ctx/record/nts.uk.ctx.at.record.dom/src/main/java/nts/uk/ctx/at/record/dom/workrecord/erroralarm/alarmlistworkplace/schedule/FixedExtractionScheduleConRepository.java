package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import java.util.List;

public interface FixedExtractionScheduleConRepository {

    List<FixedExtractionScheduleCon> getBy(List<String> ids, boolean useAtr);
}
