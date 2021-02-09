package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.weekly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeeklyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly.KrcdtWeekCondAlarm;

import java.util.ArrayList;
import java.util.List;

public class JpaExtractCondScheduleWeeklyRepository extends JpaRepository implements ExtractionCondScheduleWeeklyRepository {
    @Override
    public List<ExtractionCondScheduleWeekly> getAll() {
        List<KrcdtWeekCondAlarm> entities = new ArrayList<>();
        return null;
    }
}
