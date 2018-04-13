package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface EmployeeDailyPerErrorRepository {
	
	void insert(EmployeeDailyPerError employeeDailyPerformanceError);
	
	boolean checkExistErrorCode(String employeeID, GeneralDate processingDate, String errorCode);
	
	boolean checkExistErrorCodeByPeriod(String employeeID, DatePeriod datePeriod, String errorCode);
	/**
	 * 対象期間に日別実績のエラーが発生しているかチェックする
	 * @return 対象日一覧の確認が済んでいる：boolean
	 * エラーが0件である＝TRUEを返す
	 * エラーが0件である＝FALSEを返す
	 */
	boolean checkExistRecordErrorListDate(String companyID, String employeeID, List<GeneralDate> lstDate);
	
	List<EmployeeDailyPerError> find(String employeeID, GeneralDate processingDate);
	
	List<EmployeeDailyPerError> findList(String companyID,String employeeID);
	
	List<EmployeeDailyPerError> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
	
	List<EmployeeDailyPerError> finds(List<String> employeeID, DatePeriod processingDate);
	
	void removeParam(String sid, GeneralDate date);
	
}
