package nts.uk.ctx.bs.employee.dom.temporaryabsence;

public interface TemporaryAbsenceHistRepository {

	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * @param domain
	 */
	void addTemporaryAbsenceHist(TempAbsenceHistory domain);
	/**
	 * 取得した「休職休業」を更新する
	 * @param domain
	 */
	void updateTemporaryAbsenceHist(TempAbsenceHistory domain);
	
	/**
	 * ドメインモデル「休職休業」を削除する
	 * @param domain
	 */
	void deleteTemporaryAbsenceHist(String histId);
	
}
