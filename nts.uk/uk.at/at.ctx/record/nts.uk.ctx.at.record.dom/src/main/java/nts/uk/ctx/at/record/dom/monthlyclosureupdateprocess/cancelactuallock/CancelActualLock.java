package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;

/**
 * 
 * @author HungTT - <<Work>> 実績ロックの解除
 *
 */

@Stateless
public class CancelActualLock{

	@Inject
	private ActualLockRepository actualLockRepo;
	
	/**
	 * 実績ロックの解除
	 * @param companyId
	 * @param closureId
	 */
	public void cancelActualLock(String companyId, int closureId) {
		Optional<ActualLock> optActualLock = actualLockRepo.findById(companyId, closureId);
		if (optActualLock.isPresent()) {
			ActualLock actualLock = optActualLock.get();
			actualLock.unlockDaily();
			actualLock.unlockMonthly();
			actualLockRepo.update(actualLock);
		}
	}

}