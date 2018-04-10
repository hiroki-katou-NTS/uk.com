package nts.uk.screen.at.app.monthlyperformance.correction.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;

/**
 * 月の実績のロック状態
 */
@Data
@AllArgsConstructor
public class MonthlyPerformaceLockStatus {
	/**
	 * 社員: 社員ID
	 */
	String employeeId;
	/**
	 * 過去実績のロック
	 */
	LockStatus pastPerformaceLock;
	/**
	 * 職場の就業確定
	 */
	LockStatus employmentConfirmWorkplace;
	/**
	 * 月別実績の承認
	 */
	LockStatus monthlyResultApprova;
	/**
	 * 月別実績のロック
	 */
	LockStatus monthlyResultLock;
	/**
	 * 日別実績の確認
	 */
	LockStatus monthlyResultConfirm;
	/**
	 * 日別実績の不足
	 */
	LockStatus monthlyResultLack;
	/**
	 * 日別実績のエラー
	 */
	LockStatus monthlyResultError;
}
