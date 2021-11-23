package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;

@Stateless
public class JpaCompanySpecificDateRepository extends JpaRepository implements CompanySpecificDateRepository {

	@Override
	public void insert(CompanySpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CompanySpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, DatePeriod period) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<CompanySpecificDateItem> get(String companyId, GeneralDate ymd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanySpecificDateItem> getList(String companyId, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
