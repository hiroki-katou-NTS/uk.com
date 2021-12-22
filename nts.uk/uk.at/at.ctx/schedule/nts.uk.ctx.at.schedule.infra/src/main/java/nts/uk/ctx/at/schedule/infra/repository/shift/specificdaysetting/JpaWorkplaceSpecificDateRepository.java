package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KscmtSpecDateWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSetPK;

@Stateless
public class JpaWorkplaceSpecificDateRepository extends JpaRepository implements WorkplaceSpecificDateRepository {

	private static final String DELETE_BY_WORKPLACE = "DELETE FROM KscmtSpecDateWkp c"
			+ " WHERE c.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId";
	
	private static final String DELETE_BY_DATE = DELETE_BY_WORKPLACE + " AND c.ksmmtWpSpecDateSetPK.specificDate = :specificDate";
	
	private static final String DELETE_BY_DATE_PERIOD = DELETE_BY_WORKPLACE + 
			" AND c.ksmmtWpSpecDateSetPK.specificDate >= :startDate "
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate <= :endDate";
	
	private static final String SELECT_ALL = "SELECT c FROM KscmtSpecDateWkp c";
	
	private static final String SELECT_BY_WORKPLACE = SELECT_ALL + " WHERE c.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId";
	
	private static final String SELECT_BY_DATE = SELECT_BY_WORKPLACE + " AND c.ksmmtWpSpecDateSetPK.specificDate = :specificDate ";
	
	private static final String SELECT_BY_DATE_PERIOD = SELECT_BY_WORKPLACE + 
			" AND c.ksmmtWpSpecDateSetPK.specificDate >= :startDate "
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate <= :endDate";
	
	@Override
	public void insert(String companyId, WorkplaceSpecificDateItem domain) {
		this.commandProxy().insertAll(toEntities(domain));
	}

	@Override
	public void update(String companyId, WorkplaceSpecificDateItem domain) {
		this.commandProxy().updateAll(toEntities(domain));
	}
	
	@Override
	public void delete(String workplaceId, GeneralDate ymd) {
		
		this.getEntityManager().createQuery(DELETE_BY_DATE)
		.setParameter("workplaceId", workplaceId)
		.setParameter("specificDate", ymd)
		.executeUpdate();
		
	}
	
	@Override
	public void delete(String workplaceId, DatePeriod period) {
		this.getEntityManager().createQuery(DELETE_BY_DATE_PERIOD)
		.setParameter("workplaceId", workplaceId)
		.setParameter("startDate", period.start())
		.setParameter("endDate", period.end())
		.executeUpdate();
	}

	@Override
	public Optional<WorkplaceSpecificDateItem> get(String workplaceId, GeneralDate ymd) {
		List<KscmtSpecDateWkp> specDateWkp = this.queryProxy()
				.query(SELECT_BY_DATE, KscmtSpecDateWkp.class)
				.setParameter("workplaceId", workplaceId)
				.setParameter("specificDate", ymd)
				.getList();
		if (specDateWkp == null || specDateWkp.isEmpty()) {
			return Optional.empty();
		}
		List<SpecificDateItemNo> specificDayItems = specDateWkp.stream()
				.map(item -> item.ksmmtWpSpecDateSetPK.specificDateItemNo)
				.map(itemNo -> new SpecificDateItemNo(itemNo))
				.collect(Collectors.toList());
		WorkplaceSpecificDateItem specificDateItem = new WorkplaceSpecificDateItem(workplaceId, ymd, OneDaySpecificItem.create(specificDayItems));
		return Optional.of(specificDateItem);
	}

	@Override
	public List<WorkplaceSpecificDateItem> getList(String workplaceId, DatePeriod period) {
		List<KscmtSpecDateWkp> entities = this.queryProxy()
				.query(SELECT_BY_DATE_PERIOD, KscmtSpecDateWkp.class)
				.setParameter("workplaceId", workplaceId)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList();
		
		List<WorkplaceSpecificDateItem> domains = new ArrayList<>();
		GeneralDate startDate = period.start();
		GeneralDate endDate = period.end();
		
		while (startDate.beforeOrEquals(endDate)) {
			GeneralDate date = startDate;
			List<SpecificDateItemNo> specificDayItems = entities.stream()
					.filter(entity -> entity.ksmmtWpSpecDateSetPK.specificDate.equals(date))
					.map(entity -> new SpecificDateItemNo(entity.ksmmtWpSpecDateSetPK.specificDateItemNo))
					.collect(Collectors.toList());
			
			if (!specificDayItems.isEmpty()) {
				domains.add(new WorkplaceSpecificDateItem(workplaceId, startDate, OneDaySpecificItem.create(specificDayItems)));
			}
			
			startDate = startDate.addDays(1);
		}
		
		return domains;
	}

	/**
	 * To entities.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private static List<KscmtSpecDateWkp> toEntities(WorkplaceSpecificDateItem domain) {
		return domain.getOneDaySpecificItem()
					.getSpecificDayItems()
					.stream()
					.map(item -> new KscmtSpecDateWkp(new KsmmtWpSpecDateSetPK(
							domain.getWorkplaceId(),
							domain.getSpecificDate(),
							item.v())))
					.collect(Collectors.toList());
	}
}
