package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface TempAbsHistRepository {

	// -----------------------------GET HISTORY ----------------------------------
	/**
	 * Get TemporaryAbsenceHist by employeeId
	 * @param employeeId
	 * @param histId
	 * @return
	 */
	Optional<TempAbsenceHistory> getByEmployeeId(String employeeId);
	/**
	 * Get TemporaryAbsenceHist by histId
	 * @param employeeId
	 * @param histId
	 * @return
	 */
	Optional<TempAbsenceHistory> getByHistId( String histId);
	
	// ------------------------------ COMMAND HISTORY---------------------------------
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	void add(TempAbsenceHistory domain);
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	void update(TempAbsenceHistory domain, DateHistoryItem item);
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	void delete(TempAbsenceHistory domain, DateHistoryItem item);
	
}
