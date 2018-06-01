package nts.uk.ctx.at.record.pub.workrecord.actuallock;

import java.util.Optional;

public interface ActualLockPub {
	/**
	 * RequestList146
	 * 締めIDから当月のロック状態を取得する
	 * @param companyID
	 * @param closureId
	 * @return
	 */
	public Optional<ActualLockExport>  getActualLockByID(String companyID,int closureId);
}
