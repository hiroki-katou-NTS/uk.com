package nts.uk.ctx.at.request.ac.record.actuallock;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workrecord.actuallock.DetermineActualResultLockPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter;

@Stateless
public class DetermineActualLockAdapterImpl implements DetermineActualResultLockAdapter{

	@Inject
	private DetermineActualResultLockPub pub;
	
	@Override
	public LockStatus lockStatus(String companyId, GeneralDate baseDate, Integer closureId, PerformanceType type) {

		boolean lock = pub.getDetermineActualLocked(companyId, baseDate, closureId,
				type == PerformanceType.DAILY ? true : false);
		return lock ? LockStatus.LOCK : LockStatus.UNLOCK;
	}

}
