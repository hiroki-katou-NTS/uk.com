package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.FixedExtractionSMonItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheFixItemMonth;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaFixedExtractionSMonItemsRepository extends JpaRepository implements FixedExtractionSMonItemsRepository {
	private final String SELECT_BASIC = "SELECT a FROM KscdtScheFixItemMonth a ";
	
    @Override
    public List<FixedExtractionSMonItems> getAll() {
    	List<KscdtScheFixItemMonth> entities = this.queryProxy().query(SELECT_BASIC, KscdtScheFixItemMonth.class).getList();
        return entities.stream().map(item -> item.toDomain()).collect(Collectors.toList());
    }
}
