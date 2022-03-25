package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KscmtSpecDateCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSetPK;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository {

	private static final String DELETE_BY_COMPANY = "DELETE FROM KscmtSpecDateCom c"
			+ " WHERE c.ksmmtComSpecDateSetPK.companyId = :companyId";
	
	private static final String DELETE_BY_DATE = DELETE_BY_COMPANY + " AND c.ksmmtComSpecDateSetPK.specificDate = :specificDate";
	
	private static final String DELETE_BY_DATE_PERIOD = DELETE_BY_COMPANY + 
			" AND c.ksmmtComSpecDateSetPK.specificDate >= :startDate "
			+ " AND c.ksmmtComSpecDateSetPK.specificDate <= :endDate";
	
	private static final String SELECT_ALL = "SELECT c FROM KscmtSpecDateCom c";
	
	private static final String SELECT_BY_COMPANY = SELECT_ALL + " WHERE c.ksmmtComSpecDateSetPK.companyId = :companyId";
	
	private static final String SELECT_BY_DATE = SELECT_BY_COMPANY + " AND c.ksmmtComSpecDateSetPK.specificDate = :specificDate ";
	
	private static final String SELECT_BY_DATE_PERIOD = SELECT_BY_COMPANY + 
			" AND c.ksmmtComSpecDateSetPK.specificDate >= :startDate "
			+ " AND c.ksmmtComSpecDateSetPK.specificDate <= :endDate";

	@Override
	public void insert(CompanySpecificDateItem domain) {
		this.commandProxy().insertAll(toEntities(domain));
	}

	@Override
	public void update(CompanySpecificDateItem domain) {
		this.commandProxy().updateAll(toEntities(domain));
	}

	@Override
	public void delete(String companyId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_DATE)
		.setParameter("companyId", companyId)
		.setParameter("specificDate", ymd)
		.executeUpdate();
	}

	@Override
	public void delete(String companyId, DatePeriod period) {
		this.getEntityManager().createQuery(DELETE_BY_DATE_PERIOD)
		.setParameter("companyId", companyId)
		.setParameter("startDate", period.start())
		.setParameter("endDate", period.end())
		.executeUpdate();
	}

	@Override
	public Optional<CompanySpecificDateItem> get(String companyId, GeneralDate ymd) {
		List<KscmtSpecDateCom> specDateCom = this.queryProxy()
				.query(SELECT_BY_DATE, KscmtSpecDateCom.class)
				.setParameter("companyId", companyId)
				.setParameter("specificDate", ymd)
				.getList();
		if (specDateCom == null || specDateCom.isEmpty()) {
			return Optional.empty();
		}
		List<SpecificDateItemNo> specificDayItems = specDateCom.stream()
				.map(item -> item.ksmmtComSpecDateSetPK.specificDateItemNo)
				.map(itemNo -> new SpecificDateItemNo(itemNo))
				.collect(Collectors.toList());
		CompanySpecificDateItem specificDateItem = new CompanySpecificDateItem(companyId, ymd, OneDaySpecificItem.create(specificDayItems));
		return Optional.of(specificDateItem);
	}

	@Override
	public List<CompanySpecificDateItem> getList(String companyId, DatePeriod period) {
		List<KscmtSpecDateCom> entities = this.queryProxy()
				.query(SELECT_BY_DATE_PERIOD, KscmtSpecDateCom.class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList();
		
		List<CompanySpecificDateItem> domains = new ArrayList<>();
		GeneralDate startDate = period.start();
		GeneralDate endDate = period.end();
		
		while (startDate.beforeOrEquals(endDate)) {
			GeneralDate date = startDate;
			List<SpecificDateItemNo> specificDayItems = entities.stream()
					.filter(entity -> entity.ksmmtComSpecDateSetPK.specificDate.equals(date))
					.map(entity -> new SpecificDateItemNo(entity.ksmmtComSpecDateSetPK.specificDateItemNo))
					.collect(Collectors.toList());
			
			if (!specificDayItems.isEmpty()) {
				domains.add(new CompanySpecificDateItem(companyId, startDate, OneDaySpecificItem.create(specificDayItems)));
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
	private static List<KscmtSpecDateCom> toEntities(CompanySpecificDateItem domain) {
		return domain.getOneDaySpecificItem()
					.getSpecificDayItems()
					.stream()
					.map(item -> new KscmtSpecDateCom(new KsmmtComSpecDateSetPK(
							domain.getCompanyId(),
							domain.getSpecificDate(),
							item.v())))
					.collect(Collectors.toList());
	}
	
}
