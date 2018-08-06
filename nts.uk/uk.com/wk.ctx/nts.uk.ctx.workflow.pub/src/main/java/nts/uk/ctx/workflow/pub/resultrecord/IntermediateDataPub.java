package nts.uk.ctx.workflow.pub.resultrecord;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.spr.export.AppRootStateStatusSprExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface IntermediateDataPub {
	
	/**
	 * [No.113](中間データ版)承認対象者と期間から承認状況を取得する
	 * @param employeeID 対象者社員ID
	 * @param period 期間
	 * @param rootType ルート種類（日別確認／月別確認）
	 * @return 承認ルートの状況
	 */
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(String employeeID, DatePeriod period, Integer rootType);
	
	/**
	 * [No.155](中間データ版)承認対象者リストと日付リストから承認状況を取得する
	 * @param employeeID
	 * @param dateLst
	 * @param rootType
	 * @return
	 */
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, List<GeneralDate> dateLst, Integer rootType);
	
	/**
	 * [No.229](中間データ版)承認対象者（複数）と期間から承認状況を取得する
	 * @param employeeID
	 * @param period
	 * @param rootType
	 * @return
	 */
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period, Integer rootType);
	
}
