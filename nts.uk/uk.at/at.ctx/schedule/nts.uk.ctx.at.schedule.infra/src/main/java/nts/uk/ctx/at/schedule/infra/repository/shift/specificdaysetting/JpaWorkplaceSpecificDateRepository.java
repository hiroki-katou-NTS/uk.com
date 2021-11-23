package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;

@Stateless
public class JpaWorkplaceSpecificDateRepository extends JpaRepository implements WorkplaceSpecificDateRepository {

	private static final String DELETE_BY_DATE = "DELETE FROM KscmtSpecDateWkp c"
			+ " WHERE c.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND c.ksmmtWpSpecDateSetPK.specificDate = :specificDate";
	
	@Override
	public void insert(String companyId, WorkplaceSpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String companyId, WorkplaceSpecificDateItem domain) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WorkplaceSpecificDateItem> get(String workplaceId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkplaceSpecificDateItem> getList(String workplaceId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}


}
