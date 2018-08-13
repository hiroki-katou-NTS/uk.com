package nts.uk.ctx.workflow.pub.resultrecord;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface IntermediateDataPub {
	
	/**
	 * [No.172](中間データ版)承認対象者の特定日の日別確認が承認済かチェックする
	 * @param employeeID
	 * @param period
	 * @param rootType
	 * @return
	 */
	public List<ApproveDoneExport> checkDateApprovedStatus(String employeeID, DatePeriod period, Integer rootType);
	
	/**
	 * [No.116](中間データ版)承認対象者リストと期間から未承認社員リストを取得する
	 * @param employeeIDLst
	 * @param period
	 * @param rootType
	 * @return
	 */
	public List<ApproverApproveExport> getApproverByPeriod(List<String> employeeIDLst, DatePeriod period, Integer rootType); 
	
	/**
	 * [No.115](中間データ版)承認対象者リストと日付リストから未承認社員リストを取得する
	 * @param employeeIDLst
	 * @param period
	 * @param rootType
	 * @return
	 */
	public List<ApproverApproveExport> getApproverByDateLst(List<String> employeeIDLst, List<GeneralDate> dateLst, Integer rootType); 
	
}
