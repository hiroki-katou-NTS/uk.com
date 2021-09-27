package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 社員の医療勤務形態履歴Repository											
 * @author HieuLt
 *
 */
public interface EmpMedicalWorkStyleHistoryRepository {
	/** [1] 社員の医療勤務形態項目get(社員ID, 基準日)**/
	Optional<EmpMedicalWorkStyleHistoryItem> get(String empID , GeneralDate referenceDate);
	
	/** [2] 社員の医療勤務形態項目*get(List<社員ID>, 基準日) **/
	List<EmpMedicalWorkStyleHistoryItem> get(List<String> listEmpId , GeneralDate referenceDate);
	
	/** [3] insert(社員の医療勤務形態履歴, 社員の医療勤務形態履歴項目) **/
	void insert(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory , EmpMedicalWorkStyleHistoryItem empMedicalWorkStyleHistoryItem );
	
	/** [4] update(社員の医療勤務形態履歴)**/
	void update(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory);
	/** [5] update(社員の医療勤務形態履歴項目)**/
	void update(EmpMedicalWorkStyleHistoryItem empMedicalWorkStyleHistoryItem);
	/**[6] delete(社員ID, 履歴ID)**/
	void delete(String empId , String historyId);
	
	void insertAll(List<EmpMedicalWorkStyleHistory> empMedicalWorkStyleHistoryList, List<EmpMedicalWorkStyleHistoryItem> empMedicalWorkStyleHisItemList);
	
	void updateAllHist(List<EmpMedicalWorkStyleHistory> empMedicalWorkStyleHistoryList);

	void updateAllItem(List<EmpMedicalWorkStyleHistoryItem> empMedicalWorkStyleHisItemList);
	
	Optional<EmpMedicalWorkStyleHistoryItem> getItemByHistId(String histId);
	
	List<EmpMedicalWorkStyleHistoryItem> getItemByHistIdList(List<String> histIdList);
	
	Optional<EmpMedicalWorkStyleHistory> getHistByHistId(String histId);
	
	Optional<EmpMedicalWorkStyleHistory> getHist(String empID , GeneralDate referenceDate);
	
	Optional<EmpMedicalWorkStyleHistory> getHistBySidDesc(String cid, String sid);
	
	Optional<EmpMedicalWorkStyleHistory> getHistBySid(String cid, String sid);
	
	List<EmpMedicalWorkStyleHistory> getHistBySidsAndCid(List<String> listEmpId, String cid);
	
	List<EmpMedicalWorkStyleHistory> getHistBySidsAndCidAndBaseDate(List<String> listEmpId, GeneralDate referenceDate, String cid);
}
