package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;

@Stateless
public class JpaCompanyBasicWorkRepository implements CompanyBasicWorkRepository {

	@Override
	public void insert(CompanyBasicWork companyBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CompanyBasicWork companyBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<CompanyBasicWork> find(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
