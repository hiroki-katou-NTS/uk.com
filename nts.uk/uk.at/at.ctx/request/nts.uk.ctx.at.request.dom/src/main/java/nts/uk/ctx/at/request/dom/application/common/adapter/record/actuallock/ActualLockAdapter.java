package nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock;

import java.util.Optional;

public interface ActualLockAdapter {
	/**
	 * request list 146
	 * @param companyID
	 * @param closureID
	 * @return
	 */
	public Optional<ActualLockImport> findByID(String companyID, int closureID);
}
