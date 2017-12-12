package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

@Stateless
public class JpaWorkingConditionItemRepository extends JpaRepository
		implements WorkingConditionItemRepository {

	@Override
	public Optional<WorkingConditionItem> findWokingConditionItem(String employeeId,
			String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(WorkingConditionItem workingConditionItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WorkingConditionItem workingConditionItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String employeeId, String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WorkingConditionItem> getAllWokingConditionItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
