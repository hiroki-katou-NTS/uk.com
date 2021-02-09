package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtracSDailyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyItems;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixItemDay;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaFixedExtracSDailyItemsRepository extends JpaRepository implements FixedExtracSDailyItemsRepository {
    @Override
    public List<FixedExtractionSDailyItems> getAll() {
        List<KscdtScheFixItemDay> entities = new ArrayList<>();
        return null;
    }
}
