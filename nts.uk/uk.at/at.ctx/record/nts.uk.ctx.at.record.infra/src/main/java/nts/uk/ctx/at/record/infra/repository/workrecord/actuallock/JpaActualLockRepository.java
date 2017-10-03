package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;

@Stateless
public class JpaActualLockRepository extends JpaRepository implements ActualLockRepository {

	@Override
	public void add(ActualLock actualLock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ActualLock actualLock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActualLock> findAll(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ActualLock> findById(String companyId, int closureId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String companyId, int closureId) {
		// TODO Auto-generated method stub
		
	}

}
