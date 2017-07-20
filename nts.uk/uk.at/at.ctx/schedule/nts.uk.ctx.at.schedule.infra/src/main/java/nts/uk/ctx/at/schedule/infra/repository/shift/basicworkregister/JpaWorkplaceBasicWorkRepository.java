package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

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
	public Optional<WorkplaceBasicWork> find(String workplaceId) {
		// TODO Auto-generated method stub
		return null;
	}

}
