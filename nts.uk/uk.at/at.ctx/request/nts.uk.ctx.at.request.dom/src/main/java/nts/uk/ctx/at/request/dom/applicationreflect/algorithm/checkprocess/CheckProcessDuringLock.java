package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.LockStatus;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.PerformanceType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         ロック中処理のチェック
 */
public class CheckProcessDuringLock {

	/**
	 * @param 会社ID
	 * @param 締めID
	 * @param ロック中の計算/集計できるか
	 * @param 対象日
	 */
	public static NotUseAtr checkProcess(Require require, String companyId, int closureId, boolean isCalWhenLock,
			GeneralDate targetDate) {

		if (isCalWhenLock)
			return NotUseAtr.USE;

		// 実績ロックされているか判定する
		LockStatus status = require.lockStatus(companyId, targetDate, closureId, PerformanceType.DAILY);

		return status == LockStatus.LOCK ? NotUseAtr.NOT_USE : NotUseAtr.USE;
	}

	public static interface Require {

		// DetermineActualResultLockAdapter
		LockStatus lockStatus(String companyId, GeneralDate baseDate, Integer closureId, PerformanceType type);
	}
}
