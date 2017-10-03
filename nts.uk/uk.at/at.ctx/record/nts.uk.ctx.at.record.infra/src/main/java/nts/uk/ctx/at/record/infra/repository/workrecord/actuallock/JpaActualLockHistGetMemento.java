package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public class JpaActualLockHistGetMemento implements ActualLockHistoryGetMemento {

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClosureId getClosureId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockStatus getDailyLockState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockStatus getMonthyLockState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YearMonth getTargetMonth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDateTime getLockDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdater() {
		// TODO Auto-generated method stub
		return null;
	}

}
