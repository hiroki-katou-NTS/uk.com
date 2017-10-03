package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;

@Stateless
public class JpaActualLockHistRepository extends JpaRepository implements ActualLockHistoryRepository {

	@Override
	public void add(ActualLockHistory actualLockHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ActualLockHistory actualLockHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActualLockHistory> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActualLockHistory> findByClosureId(String companyId, int closureId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ActualLockHistory> findByLockDate(String companyId, int closureId, GeneralDateTime lockDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String companyId, int closureId, GeneralDate lockDate) {
		// TODO Auto-generated method stub
		
	}

}
