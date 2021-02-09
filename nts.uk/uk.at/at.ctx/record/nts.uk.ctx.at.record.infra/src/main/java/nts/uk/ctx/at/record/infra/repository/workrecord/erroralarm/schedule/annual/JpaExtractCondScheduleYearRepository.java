package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.annual;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYearRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.annual.KscdtScheAnyCondYear;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaExtractCondScheduleYearRepository extends JpaRepository implements ExtractionCondScheduleYearRepository {
    @Override
    public List<ExtractionCondScheduleYear> getAll() {
        List<KscdtScheAnyCondYear> entities = new ArrayList<>();
        return null;
    }
}
