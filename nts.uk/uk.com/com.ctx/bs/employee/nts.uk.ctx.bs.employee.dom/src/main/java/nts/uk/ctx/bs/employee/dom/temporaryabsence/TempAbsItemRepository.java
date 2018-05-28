package nts.uk.ctx.bs.employee.dom.temporaryabsence;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TempAbsItemRepository {

	// ------------------------------GET HISTORY ITEM
	/**
	 * get TempAbsenceHisItem by history Id
	 * 
	 * @param historyId
	 * @return
	 */
	Optional<TempAbsenceHisItem> getItemByHitoryID(String historyId);
	
	
	/**
	 * get by a list of history id
	 * @param historyIds
	 * @return
	 */
	List<TempAbsenceHisItem> getItemByHitoryIdList(List<String> historyIds);
	
	/**
	 * get with employeeId and standardDate
	 * @param employeeId
	 * @param standardDate
	 * @return
	 */
	Optional<TempAbsenceHisItem> getByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate);

	// ------------------------------ COMMAND HISTORY ITEM
	/**
	 * ドメインモデル「休職休業」を新規登録する
	 * 
	 * @param domain
	 */
	void add(TempAbsenceHisItem domain);

	/**
	 * 取得した「休職休業」を更新する
	 * 
	 * @param domain
	 */
	void update(TempAbsenceHisItem domain);

	/**
	 * ドメインモデル「休職休業」を削除する
	 * 
	 * @param domain
	 */
	void delete(String histId);

}
