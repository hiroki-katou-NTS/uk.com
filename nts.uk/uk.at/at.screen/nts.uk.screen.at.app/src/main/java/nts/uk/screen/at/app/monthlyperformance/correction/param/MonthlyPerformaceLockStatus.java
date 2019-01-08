package nts.uk.screen.at.app.monthlyperformance.correction.param;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;

/**
 * 月の実績のロック状態
 */
@Data
@AllArgsConstructor
public class MonthlyPerformaceLockStatus {
	public static String LOCK_MONTHLY_PAST = "pastPerformaceLock";
	public static String LOCK_EMPLOYEE_CONFIRM = "EmploymentConfirmWorkplace";
	public static String LOCK_MONTHLY_APPROVAL = "monthlyResultApprova";
	public static String LOCK_MONTHLY_RESULT = "monthlyResultLock";
	public static String LOCK_MONTHLY_RESULT_CONFIRM = "monthlyResultConfirm";
	public static String LOCK_MONTHLY_LACK = "monthlyResultLack";
	public static String LOCK_MONTHLY_ERROR = "monthlyResultError";
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
	
	public String getLockStatusString(){
		StringBuilder sb = new StringBuilder();
		if(pastPerformaceLock == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_PAST);
			sb.append("|");
		}
		if(employmentConfirmWorkplace == LockStatus.LOCK){
			sb.append(LOCK_EMPLOYEE_CONFIRM);
			sb.append("|");
		}
		if(monthlyResultApprova == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_APPROVAL);
			sb.append("|");
		}
		if(monthlyResultLock == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_RESULT);
			sb.append("|");
		}
		if(monthlyResultConfirm == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_RESULT_CONFIRM);
			sb.append("|");
		}
		if(monthlyResultLack == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_LACK);
			sb.append("|");
		}
		if(monthlyResultError == LockStatus.LOCK){
			sb.append(LOCK_MONTHLY_ERROR);
			sb.append("|");
		}
		String resultStatus = Strings.EMPTY;
		if(sb.length() > 0){
			resultStatus = sb.toString().substring(0, sb.length() - 1);
		}
		return resultStatus;
	}
}
