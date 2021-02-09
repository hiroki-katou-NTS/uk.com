package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheFixItemMonth;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaFixedExtractionSMonItemsRepository extends JpaRepository implements FixedExtractionSMonItemsRepository {
    @Override
    public List<FixedExtractionSMonItems> getAll() {
        List<KscdtScheFixItemMonth> entities = new ArrayList<>();

        return null;
    }
}
