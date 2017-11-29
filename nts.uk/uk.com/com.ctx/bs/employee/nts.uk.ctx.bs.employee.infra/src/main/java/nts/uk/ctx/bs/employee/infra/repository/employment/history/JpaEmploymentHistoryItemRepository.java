package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;

@Stateless
public class JpaEmploymentHistoryItemRepository extends JpaRepository implements EmploymentHistoryItemRepository{

	@Override
	public void adḍ̣̣̣(EmploymentHistoryItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EmploymentHistoryItem domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String histId) {
		// TODO Auto-generated method stub
		
	}

}
