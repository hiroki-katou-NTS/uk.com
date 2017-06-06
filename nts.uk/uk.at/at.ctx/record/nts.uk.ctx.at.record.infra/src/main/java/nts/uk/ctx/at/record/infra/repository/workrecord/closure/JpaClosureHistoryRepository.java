package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;

public class JpaClosureHistoryRepository extends JpaRepository implements ClosureHistoryRepository{

	@Override
	public void add(ClosureHistory closureHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ClosureHistory closureHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClosureHistory> findByClosureId(ClosureId closureId) {
		// TODO Auto-generated method stub
		return null;
	}

}
