package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TemporaryAbsenceRepository {

	Optional<TempLeaveAbsenceHistory> getBySidAndReferDate(String sid, GeneralDate referenceDate);
	
	Optional<TempLeaveAbsenceHistory> getByTempAbsenceId(String tempAbsenceId);
	
	List<TempLeaveAbsenceHistory> getListBySid(String sid);
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	void addTemporaryAbsence(TempLeaveAbsenceHistory domain);
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	void updateTemporaryAbsence(TempLeaveAbsenceHistory domain);
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	void deleteTemporaryAbsence(TempLeaveAbsenceHistory domain);
	
}
