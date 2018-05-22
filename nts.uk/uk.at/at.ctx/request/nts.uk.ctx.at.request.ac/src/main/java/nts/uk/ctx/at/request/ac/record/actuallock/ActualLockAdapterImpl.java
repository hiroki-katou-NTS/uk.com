package nts.uk.ctx.at.request.ac.record.actuallock;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.workrecord.actuallock.ActualLockExport;
import nts.uk.ctx.at.record.pub.workrecord.actuallock.ActualLockPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
@Stateless
public class ActualLockAdapterImpl implements ActualLockAdapter {
	@Inject
	private ActualLockPub actualLockPub;
	@Override
	public Optional<ActualLockImport> findByID(String companyID, int closureID) {
		Optional<ActualLockExport> actualLockExport  = this.actualLockPub.getActualLockByID(companyID, closureID);
		if(actualLockExport.isPresent()){
			Optional<ActualLockImport> actualLockImport = Optional.of(new ActualLockImport(companyID,
					closureID, 
					actualLockExport.get().getDailyLockState(), actualLockExport.get().getMonthlyLockState()));
			return actualLockImport;
		}
		return Optional.empty();
	}

}
