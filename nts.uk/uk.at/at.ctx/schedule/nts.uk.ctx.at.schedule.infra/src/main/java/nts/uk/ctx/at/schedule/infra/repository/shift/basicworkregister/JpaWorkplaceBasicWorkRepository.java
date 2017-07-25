package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;

@Stateless
public class JpaWorkplaceBasicWorkRepository implements WorkplaceBasicWorkRepository {

	@Override
	public void insert(WorkplaceBasicWork workplaceBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkplaceBasicWork workplaceBasicWork) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String workplaceId) {
		// TODO Auto-generated method stub
	}


	@Override
	public List<WorkplaceBasicWork> findAll(String CompanyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkplaceBasicWork> find(String workplaceId, Integer workdayDivision) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
