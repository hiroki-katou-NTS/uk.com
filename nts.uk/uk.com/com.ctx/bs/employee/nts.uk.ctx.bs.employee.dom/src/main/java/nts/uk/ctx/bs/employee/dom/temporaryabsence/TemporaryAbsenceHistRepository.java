package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface TemporaryAbsenceHistRepository {

	/**
	 * Get TemporaryAbsenceHist by employeeId
	 * @param employeeId
	 * @param histId
	 * @return
	 */
	Optional<TempAbsenceHistory> getTemporaryAbsenceHistByEmployeeId(String employeeId);
	/**
	 * Get TemporaryAbsenceHist by histId
	 * @param employeeId
	 * @param histId
	 * @return
	 */
	Optional<TempAbsenceHistory> getTemporaryAbsenceHistByHistId(String employeeId, String histId);
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	void addTemporaryAbsenceHist(TempAbsenceHistory domain);
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	void updateTemporaryAbsenceHist(TempAbsenceHistory domain, DateHistoryItem item);
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	void deleteTemporaryAbsenceHist(TempAbsenceHistory domain, DateHistoryItem item);
	
}
