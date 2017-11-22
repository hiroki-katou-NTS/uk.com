package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TemporaryAbsenceRepository {

	Optional<TempAbsenceHistory> getBySidAndReferDate(String sid, GeneralDate referenceDate);
	
	Optional<TempAbsenceHistory> getByTempAbsenceId(String tempAbsenceId);
	
	List<TempAbsenceHistory> getListBySid(String sid);
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	void addTemporaryAbsence(TempAbsenceHistory domain);
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	void updateTemporaryAbsence(TempAbsenceHistory domain);
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	void deleteTemporaryAbsence(TempAbsenceHistory domain);
	
}
