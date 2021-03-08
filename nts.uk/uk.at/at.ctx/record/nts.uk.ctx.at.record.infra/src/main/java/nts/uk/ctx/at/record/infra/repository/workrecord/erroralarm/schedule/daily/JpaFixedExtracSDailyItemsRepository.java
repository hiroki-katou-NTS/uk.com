package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtracSDailyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyItems;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixItemDay;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaFixedExtracSDailyItemsRepository extends JpaRepository implements FixedExtracSDailyItemsRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KscdtScheFixItemDay a";
	private static final String BY_CONTRACT_COMPANY = " WHERE a.pk.cid = :companyCode AND a.contractCd = :contractCode";
	
    @Override
    public List<FixedExtractionSDailyItems> getAll() {
        List<KscdtScheFixItemDay> entities = this.queryProxy().query(SELECT_BASIC, KscdtScheFixItemDay.class).getList();
        return entities.stream().map(item -> item.toDomain()).collect(Collectors.toList());
    }

	@Override
	public List<FixedExtractionSDailyItems> getScheFixItemDay(String contractCode, String companyCode) {
		List<KscdtScheFixItemDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY, KscdtScheFixItemDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyCode", companyCode)
				.getList();
        return entities.stream().map(item -> item.toDomain()).collect(Collectors.toList());
	}
}
