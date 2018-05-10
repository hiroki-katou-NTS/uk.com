package nts.uk.ctx.at.record.pubimp.workrecord.actuallock;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.pub.workrecord.actuallock.ActualLockExport;
import nts.uk.ctx.at.record.pub.workrecord.actuallock.ActualLockPub;

@Stateless
public class ActualLockImpl implements ActualLockPub{
	@Inject
	private ActualLockRepository actualLockRepository;
	@Override
	public Optional<ActualLockExport> getActualLockByID(String companyID, int closureId) {
		Optional<ActualLock> actualLock  = this.actualLockRepository.findById(companyID, closureId);
		if(actualLock.isPresent()){
			Optional<ActualLockExport> actualLockExport  = Optional.of(new ActualLockExport(companyID, 
					closureId,
					actualLock.get().getDailyLockState().value,
					actualLock.get().getMonthlyLockState().value));
			return actualLockExport;
		}
		return Optional.empty();
	}

}
