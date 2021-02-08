package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;


import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaExtraCondScheDayRepository implements ExtraCondScheDayRepository {
    @Override
    public List<ExtractionCondScheduleDay> getAll(String cid) {
        return null;
    }
}
