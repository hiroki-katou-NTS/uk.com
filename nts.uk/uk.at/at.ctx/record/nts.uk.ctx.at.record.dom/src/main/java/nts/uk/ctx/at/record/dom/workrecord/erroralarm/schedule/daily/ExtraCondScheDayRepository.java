package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import java.util.List;

public interface ExtraCondScheDayRepository {
    List<ExtractionCondScheduleDay> getAll(String cid);
}
