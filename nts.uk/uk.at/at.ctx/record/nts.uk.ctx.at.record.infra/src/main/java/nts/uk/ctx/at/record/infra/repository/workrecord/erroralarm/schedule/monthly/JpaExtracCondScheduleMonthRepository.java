package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.ExtractionCondScheduleMonthRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheAnyCondMonth;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaExtracCondScheduleMonthRepository  extends JpaRepository implements ExtractionCondScheduleMonthRepository {
    @Override
    public List<ExtractionCondScheduleMonth> getAll() {
        List<KscdtScheAnyCondMonth> entities = new ArrayList<>();
        return null;
    }
}
