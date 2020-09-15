package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;

/**
 * 
 * @author HungTT - <<Work>> 実績ロックの解除
 *
 */
public class CancelActualLock{
	
	/**
	 * 実績ロックの解除
	 * @param companyId
	 * @param closureId
	 */
	public static AtomTask cancelActualLock(RequireM1 require, String companyId, int closureId) {
		Optional<ActualLock> optActualLock = require.actualLock(companyId, closureId);
		if (optActualLock.isPresent()) {
			ActualLock actualLock = optActualLock.get();
			actualLock.unlockDaily();
			actualLock.unlockMonthly();
			return AtomTask.of(() -> require.updateActualLock(actualLock));
		}
		
		return AtomTask.of(() -> {});
	}
	
	public static interface RequireM1 {
		
		Optional<ActualLock> actualLock(String companyId, int closureId);
		
		void updateActualLock(ActualLock actualLock);
	}

}