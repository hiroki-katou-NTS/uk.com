package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import java.util.List;

public interface ExtraCondScheDayRepository {
    List<ExtractionCondScheduleDay> getAll(String cid);
}
