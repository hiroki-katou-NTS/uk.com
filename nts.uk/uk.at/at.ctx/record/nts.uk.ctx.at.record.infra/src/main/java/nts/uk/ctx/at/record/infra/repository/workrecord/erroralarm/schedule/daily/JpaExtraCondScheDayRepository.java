package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheAnyCondDay;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaExtraCondScheDayRepository extends JpaRepository implements ExtraCondScheDayRepository {
    @Override
    public List<ExtractionCondScheduleDay> getAll(String cid) {
        List<KscdtScheAnyCondDay> entities = new ArrayList<>();

        return null;
    }
}
