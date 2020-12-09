package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule;

import java.util.List;

public interface ExtractionScheduleConRepository {

    List<ExtractionScheduleCon> getBy(List<String> ids, boolean useAtr);
}
