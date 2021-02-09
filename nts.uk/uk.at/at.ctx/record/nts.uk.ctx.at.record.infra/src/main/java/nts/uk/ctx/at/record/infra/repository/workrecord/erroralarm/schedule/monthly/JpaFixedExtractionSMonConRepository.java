package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheFixCondMonth;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaFixedExtractionSMonConRepository extends JpaRepository implements FixedExtractionSMonConRepository {
    @Override
    public List<FixedExtractionSMonCon> getAll() {
        List<KscdtScheFixCondMonth> entities = new ArrayList<>();
        return null;
    }
}
