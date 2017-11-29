package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class JpaEmploymentHistoryRepository extends JpaRepository implements EmploymentHistoryRepository{

	@Override
	public Optional<EmploymentHistory> getByEmployeeId(String sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(EmploymentHistory domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EmploymentHistory domain, DateHistoryItem itemToBeUpdated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(EmploymentHistory domain, DateHistoryItem itemToBeDeleted) {
		// TODO Auto-generated method stub
		
	}

}
