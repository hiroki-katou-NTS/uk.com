package nts.uk.ctx.at.record.pubimp.workrecord.actuallock;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.PerformanceType;
import nts.uk.ctx.at.record.pub.workrecord.actuallock.DetermineActualResultLockPub;

@Stateless
public class DetermineActualLockPubImpl implements DetermineActualResultLockPub {

	@Inject
	private DetermineActualResultLock lock;

	@Override
	public boolean getDetermineActualLocked(String companyId, GeneralDate baseDate, Integer closureId,
			boolean typeDaily) {
		LockStatus status = lock.getDetermineActualLocked(companyId, baseDate, closureId,
				typeDaily ? PerformanceType.DAILY : PerformanceType.MONTHLY);

		return status == LockStatus.LOCK ? true : false;
	}

}
